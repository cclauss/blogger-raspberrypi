import json
from system_info import SystemInfo

data = {}
h1 = SystemInfo()
data["cpu_temperature"] = h1.cpu_temperature
data["disk_space"] = h1.disk_space
data["cpu_use"] = h1.cpu_use
data["ram_info"] = h1.ram_info
print(json.dumps(data))
