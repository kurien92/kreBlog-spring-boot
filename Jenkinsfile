pipeline {
    agent any

    tools {
        jdk "openjdk17"
        gradle "Gradle-7.6.1"
    }

    environment {
        PATH = "/usr/local/bin:/var/lib/jenkins/bin:$PATH"
        SSH_CREDENTIALS_ID = 'thotz-server' // Jenkins 자격 증명에 정의된 ID
        SSH_HOST = 'www.kurien.net' // 원격 서버 호스트 주소
        SSH_REMOTE_DIRECTORY = '/home/ubuntu' // 원격 디렉토리 경로
        FILENAME = 'kreblog.tar'
    }

    stages {
        stage('Build') {
            steps {
                script {
                    // Gradle 빌드 수행
                    sh "chmod +x gradlew"
                    sh "./gradlew clean build -x test"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                // Docker 이미지 빌드
                sh 'docker build -t kreblog:latest .'
                sh 'docker save kreblog:latest > kreblog.tar'
            }
        }

        stage('Upload Dockerfile and deploy') {
            steps {
                script {
                    sshPublisher(
                        continueOnError: false, failOnError: true,
                        publishers: [
                            sshPublisherDesc(
                                configName: "${env.SSH_CREDENTIALS_ID}",
                                verbose: true,
                                transfers: [
                                    sshTransfer(
                                        sourceFiles: "${env.FILENAME}"
                                    ),
                                    sshTransfer(
                                        execCommand: 'cd ' + "${env.SSH_REMOTE_DIRECTORY}" + ' && ./deployBlog.sh ' + "${env.FILENAME}"
                                    )
                                ]
                            )
                        ]
                    )
                }
            }
        }
    }
}
