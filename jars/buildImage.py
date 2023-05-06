"""
根据目录下的jar包自动构建Docker Image
"""

import os
import subprocess

"""
服务的端口
"""
ports = {
    "assessment-0.0.1": 9011,
    "business-0.0.1": 9012,
    "eurekaserver-0.0.1": 9001,
    "ic-0.0.1": 9007,
    "gateway-0.0.1": 9099,
    "ie-0.0.1": 9006,
    "configserver-0.0.1": 9000,
    "oss-0.0.1": 9098,
    "noexit-0.0.1": 9008,
    "category-0.0.1": 9009,
    "daily-0.0.1": 9010,
    "termination-0.0.1": 9013,
    "uncorrected-0.0.1": 9014,
}

"""
构建dockerfile
"""


def build_image():
    p = []
    for entry in entries:
        filename = entry.split('.jar')[0]
        image_name = filename.split('-SNAPSHOT')[0]
        print(image_name)
        with open(f"./dockerfile/{image_name}_dockerfile", "w") as f:
            f.write("FROM openjdk:17-alpine\n")
            f.write(f"ADD {filename}.jar /{filename}.jar\n")
            f.write(f"RUN sh -c 'touch /{filename}.jar'\n")
            f.write(f"EXPOSE {ports[image_name]}\n")
            f.write(f"ENTRYPOINT [\"java\", \"-jar\",\"/{filename}.jar\", \"--spring.profiles.active=prod\"]\n")
            f.write("MAINTAINER xieyuquan\n")
            cmd = f"docker build -t ccorr/{image_name} -f ./dockerfile/{image_name}_dockerfile .\n"
            p.append(subprocess.Popen(cmd, shell=True))
    for pro in p:
        pro.wait()


entries = list(filter(lambda x: "jar" in x, os.listdir('.')))
build_image()
# build_image(["eurekaserver-0.0.1-SNAPSHOT.jar", "configserver-0.0.1-SNAPSHOT.jar", "gateway-0.0.1-SNAPSHOT.jar"])
