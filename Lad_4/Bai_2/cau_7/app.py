import os

env = os.getenv("APP_ENV", "undefined")
print(f"App is running in {env} mode")