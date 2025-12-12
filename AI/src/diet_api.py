from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field
from typing import List, Optional
import requests
from PIL import Image
import io

# TODO: Qwen VLM 추론 코드로 교체 예정. 현재는 더미 반환.

class DietContextModel(BaseModel):
    user_id: Optional[int] = Field(None, alias="userId")
    user_name: Optional[str] = Field(None, alias="userName")
    birth_year: Optional[int] = Field(None, alias="birthYear")
    gender: Optional[str]
    height_cm: Optional[float] = Field(None, alias="heightCm")
    weight_kg: Optional[float] = Field(None, alias="weightKg")
    activity_level: Optional[str] = Field(None, alias="activityLevel")
    health_goal_type: Optional[str] = Field(None, alias="healthGoalType")
    health_updated_at: Optional[str] = Field(None, alias="healthUpdatedAt")
    diagnosis_name: Optional[str] = Field(None, alias="diagnosisName")
    analysis_created_at: Optional[str] = Field(None, alias="analysisCreatedAt")
    photo_id: Optional[int] = Field(None, alias="photoId")
    photo_url: Optional[str] = Field(None, alias="photoUrl")
    rec_id: Optional[int] = Field(None, alias="recId")
    rec_memo: Optional[str] = Field(None, alias="recMemo")
    rec_created_at: Optional[str] = Field(None, alias="recCreatedAt")


class CaloriePlanModel(BaseModel):
    bmr: Optional[int] = None
    tdee: Optional[int] = None
    target_calories: Optional[int] = Field(None, alias="targetCalories")
    activity_factor: Optional[float] = Field(None, alias="activityFactor")
    age_used: Optional[int] = Field(None, alias="ageUsed")
    gender_used: Optional[str] = Field(None, alias="genderUsed")
    goal_type: Optional[str] = Field(None, alias="goalType")


class DietItem(BaseModel):
    menuName: str
    description: str
    calories: int
    notes: Optional[str] = None
    recipeUrl: Optional[str] = None
    skincareUrl: Optional[str] = None


class DietLlmRequest(BaseModel):
    context: DietContextModel
    caloriePlan: Optional[CaloriePlanModel] = None
    rulesText: Optional[str] = None


app = FastAPI(title="Diet Generation Stub (Qwen placeholder)", version="0.1")


def _load_image_for_vlm(url: str) -> Image.Image:
    if not url:
        raise HTTPException(status_code=400, detail="photo_url is required for diet generation")
    if url.startswith("http"):
        resp = requests.get(url, timeout=10)
        resp.raise_for_status()
        return Image.open(io.BytesIO(resp.content)).convert("RGB")
    return Image.open(url).convert("RGB")


@app.post("/diet/generate", summary="이미지+컨텍스트로 식단 JSON 생성(스텁)")
def diet_generate(body: DietLlmRequest) -> List[DietItem]:
    """
    간단한 스텁: 이미지 접근성 검증 후 diagnosis/rules를 참고해 더미 식단을 반환한다.
    추후 Qwen VLM 추론으로 교체 가능.
    """
    # 1) 이미지 접근 확인 (다운로드만 수행)
    try:
        _load_image_for_vlm(body.context.photo_url)
    except Exception as exc:
        raise HTTPException(status_code=400, detail=f"이미지를 읽을 수 없습니다: {exc}")

    diagnosis = body.context.diagnosis_name or "general"
    target = body.caloriePlan.target_calories if body.caloriePlan and body.caloriePlan.target_calories else 550
    base_notes = body.rulesText or "follow rules"

    items = [
        {
            "menuName": "salmon salad",
            "description": "grilled salmon with greens and olive oil dressing",
            "calories": target,
            "notes": f"{diagnosis} | {base_notes}"
        },
        {
            "menuName": "tofu rice bowl",
            "description": "brown rice, tofu, veggies, low GI sauce",
            "calories": max(400, target - 50),
            "notes": f"{diagnosis} | protein & veggies"
        },
    ]
    return items
