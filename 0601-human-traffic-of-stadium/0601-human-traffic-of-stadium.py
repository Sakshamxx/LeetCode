import pandas as pd

def human_traffic(stadium: pd.DataFrame) -> pd.DataFrame:
    df = stadium[stadium["people"] >= 100].copy()
    df["group"] = df["id"] - range(len(df))
    df = df.groupby("group").filter(lambda x: len(x) >= 3)
    return df[["id", "visit_date", "people"]].sort_values("visit_date")