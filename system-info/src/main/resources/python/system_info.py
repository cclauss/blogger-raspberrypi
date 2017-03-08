import os


class SystemInfo(object):

    # Return RAM information (unit=kb) in a list
    # Index 0: total RAM
    # Index 1: used RAM
    # Index 2: free RAM
    @property
    def ram_info(self):
        p = os.popen('free')
        i = 0
        while 1:
            i += 1
            line = p.readline()
            if i == 2:
                return (line.split()[1:4])

    # Return % of CPU used by user as a character string
    @property
    def cpu_use(self):
        cmd = "top -n1 | awk '/Cpu\(s\):/ {print $2}'"
        return str(os.popen(cmd).readline().strip())

    # Return information about disk space as a list (unit included)
    # Index 0: total disk space
    # Index 1: used disk space
    # Index 2: remaining disk space
    # Index 3: percentage of disk used
    @property
    def disk_space(self):
        p = os.popen("df -h /")
        i = 0
        while 1:
            i += 1
            line = p.readline()
            if i == 2:
                return (line.split()[1:5])

    # Return CPU temperature as a character string
    @property
    def cpu_temperature(self):
        res = os.popen('vcgencmd measure_temp').readline()
        # noinspection PyTypeChecker
        return res.replace("temp=", "").replace("'C\n", "")
