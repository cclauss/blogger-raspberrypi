import json
from system_info import SystemInfo

h1 = SystemInfo()
print(json.dumps({"cpu_temperature": h1.cpu_temperature,
                  "disk_space": h1.disk_space,
                  "cpu_use": h1.cpu_use,
                  "ram_info": h1.ram_info}))
