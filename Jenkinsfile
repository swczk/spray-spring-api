pipeline {
	agent any

	environment {
		DOCKER_HUB_CREDENTIALS = credentials('dockerhub-credentials')
		DOCKER_IMAGE_NAME = 'swczk/spray-spring-api'
		GIT_BRANCH = 'main'
		GIT_REPO_URL = 'https://github.com/swczk/spray-spring-api.git'
	}

	stages {
		stage('Checkout') {
			steps {
				git branch: "${GIT_BRANCH}", url: "${GIT_REPO_URL}"
			}
		}
		stage('Build Docker Image') {
			steps {
				script {
					docker.build("${DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}")
				}
			}
		}
		stage('Push to Docker Hub') {
			steps {
				script {
					docker.withRegistry('', 'dockerhub-credentials') {
						docker.image("${DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}").push()
						docker.image("${DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}").tag("${DOCKER_IMAGE_NAME}:latest")
						docker.image("${DOCKER_IMAGE_NAME}:latest").push()
					}
				}
			}
		}
	}
}
