from enum import Enum
from pydantic import BaseModel, ConfigDict, Field
from pydantic.alias_generators import to_camel

class AnalysisCategory(str, Enum):
    GENERAL = "GENERAL"
    TECHNICAL = "TECHNICAL"
    BILLING = "BILLING"
    ACCESS = "ACCESS"

class AnalysisPriority(str, Enum):
    LOW ="LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"
    URGENT = "URGENT"

class AnalysisRequest(BaseModel):
    model_config = ConfigDict(str_strip_whitespace=True)

    title: str = Field(..., min_length=1, max_length=150)
    description: str | None = Field(default=None, max_length=5000)

class AnalysisResponse(BaseModel):
    model_config = ConfigDict(
        alias_generator=to_camel,
        populate_by_name=True,
    )

    category: AnalysisCategory 
    priority: AnalysisPriority
    summary: str  
    suggested_response: str 
    reasoning: str