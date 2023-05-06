import os
import subprocess

project_url = "/Users/xieyuquan/IdeaProjects/ccorrection"


def jar_name(name):
    return f"{name}-0.0.1-SNAPSHOT.jar"


def maven(cmd):
    processes = []
    for entry in entries:
        pro_url = f"{project_url}/{entry}"
        pro = os.listdir(pro_url)
        print(f"Enter: {entry}")
        cmd = f"cd {pro_url};{cmd}"
        processes.append(subprocess.Popen(cmd, shell=True))

    for process in processes:
        process.wait()


def clean():
    maven("mvn clean")


def package():
    maven("mvn package")


def mv():
    processes = []
    for entry in entries:
        pro_url = f"{project_url}/{entry}"
        pro = os.listdir(pro_url)
        print(f"Enter: {entry} url: {pro_url} module:")
        print(pro)
        cmd = f"cd {pro_url};mv ./target/{jar_name(entry)} {project_url}/jars"
        processes.append(subprocess.Popen(cmd, shell=True))

    for process in processes:
        process.wait()


def build_jar():
    clean()
    package()
    mv()


entries = list(filter(lambda d: "." not in d and d != "jars", os.listdir(project_url)))
build_jar()
