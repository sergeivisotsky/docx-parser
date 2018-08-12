pipeline {

    agent any
    tools {
        maven 'maven_3_5_0'
    }

    stages {
        stage('Install Stage') {
            steps {
                bat 'mvn clean install'
            }
        }
        /*stage('Install Stage') {
            steps {
                bat 'mvn clean install'
            }
        }
        stage('Compile Stage') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Testing Stage') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Deployment Stage') {
            steps {
                mat 'mvn deploy'
            }
        }*/
    }
}