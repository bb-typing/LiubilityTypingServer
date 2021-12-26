//该配置文件是使用docker来部署项目的
pipeline {
    agent none
    parameters {
        string(name: 'targetPath', defaultValue: 'LiubilityTypingServer/liubility-typing-server/target/', description: '生成jar包路径')
        string(name: 'serverPath', defaultValue: '/var/local/src/liubility/typing', description: '远程服务路径')
        string(name: 'remoteIp', defaultValue: '192.168.10.150', description: '部署到远程IP地址')
        string(name: 'remoteUser', defaultValue: 'root', description: '部署到远程用户')
        string(name: 'remotePort', defaultValue: '22', description: 'ssh的端口')
    }
    stages {
        stage('Install&Build') {
            agent {
                docker {
                    image 'maven:3-alpine'
                    args '-v /root/.m2:/root/.m2 -v /root/maven_conf/settings.xml:/usr/share/maven/conf/settings.xml'
                }
            }
            steps {
                sh 'mvn -B -f liubility-parent/pom.xml -DskipTests clean install'
                sh 'mvn -B -DskipTests clean compile package'
            }
        }
        stage('Upload') {
            agent any
            steps {
                sh "scp -P ${params.remotePort} ${params.targetPath}Dockerfile ${params.remoteUser}@${params.remoteIp}:${params.serverPath}"
                sh "scp -P ${params.remotePort} ${params.targetPath}*.jar ${params.remoteUser}@${params.remoteIp}:${params.serverPath}"
                sh "scp -P ${params.remotePort} ${params.targetPath}*.sh ${params.remoteUser}@${params.remoteIp}:${params.serverPath}"
            }
        }
        stage('Run'){
            agent any
            steps{
                sh "ssh -p ${params.remotePort} ${params.remoteUser}@${params.remoteIp} 'cd ${params.serverPath};chmod +x start.sh;./start.sh'"
            }
        }
    }
}