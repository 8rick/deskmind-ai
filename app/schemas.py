from enum import Enum
from pydantic import BaseModel, ConfigDict, Field

class AnalysisCategory(str, Enum):
    GENERAL = "GENERAL"
    TECHNICAL = "TECHNICAL"
    BILLING = "BILLING"
    ACCESS = "ACCESS"

class AnalysisPriority(str, Enum):
    LOW ="LOW"
    MEDIUM = "MEDIUM"
    HIGHT = "HIGHT"
    URGENT = "URGENT"

class AnalysisRequest(BaseModel):
    model_config = ConfigDict(str_strip_whitespace=True)

    title: str = Field(..., min_length=1, max_length=150)
    descrption: str | None = Field(default=None, max_length=5000)

class AnalysisResponse(BaseModel):
    category: AnalysisCategory 
    priority: AnalysisPriority
    summary: str  
    suggested_response: str 
    reasoning: str