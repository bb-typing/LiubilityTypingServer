//该配置文件是使用docker来部署项目的
pipeline {
    agent none
    parameters {
        string(name: 'targetPath', defaultValue: 'qianlang-erp-server/qianlang-erp-service/target/', description: '生成jar包路径')
        string(name: 'serverPath', defaultValue: '/var/qianlangproject/qianlangServer/docker/', description: '远程服务路径')
        string(name: 'remoteIp', defaultValue: '39.96.83.89', description: '部署到远程IP地址')
        string(name: 'remoteUser', defaultValue: 'root', description: '部署到远程用户')
        string(name: 'remotePort', defaultValue: '6001', description: 'ssh的端口')
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
    }
}