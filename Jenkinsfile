pipeline { 
    agent any  

    tools {
        maven 'Maven' 
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/vishalsangit/SampleProject.git'
            }
        }

        stage('Build and Test') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Reports') {
            steps {
                publishHTML(target: [
                    reportDir: 'src/test/resources/ExtentReports',  
                    reportFiles: 'ExtentReport.html',  
                    reportName: 'Extent HTML Report'
                ])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/src/test/resources/ExtentReports/*.html', fingerprint: true
            junit 'target/surefire-reports/*.xml'
        }

success {
    emailext (
        to: 'sangal.vishal@gmail.com',
        subject: "Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
        body: """
        <html>
        <body>
        <p>Hello Team,</p>

        <p>The latest Jenkins build has completed.</p>

        <p><b>Project Name:</b> ${env.JOB_NAME}</p>
        <p><b>Build Number:</b> #${env.BUILD_NUMBER}</p>
        <p><b>Build Status:</b> <span style="color: green;"><b>SUCCESS</b></span></p>
        <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>

        <p><b>Last Commit:</b></p>
        <p>${env.GIT_COMMIT}</p>
        <p><b>Branch:</b> ${env.GIT_BRANCH}</p>

        <p><b>Build log is attached.</b></p>

        <p><b>Extent Report:</b> <a href="http://localhost:8080/job/SampleProject_pipeline/lastSuccessfulBuild/artifact/src/test/resources/ExtentReports/">Click here</a></p>

        <p>Best regards,</p>
        <p><b>Automation Team</b></p>
        </body>
        </html>
        """,
        mimeType: 'text/html',
        attachLog: true
    )
}

failure {
    emailext (
        to: 'sangal.vishal@gmail.com',
        subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
        body: """
        <html>
        <body>
        <p>Hello Team,</p>

        <p>The latest Jenkins build has <b style="color: red;">FAILED</b>.</p>

        <p><b>Project Name:</b> ${env.JOB_NAME}</p>
        <p><b>Build Number:</b> #${env.BUILD_NUMBER}</p>
        <p><b>Build Status:</b> <span style="color: red;"><b>FAILED &#10060;</b></span></p>
        <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>

        <p><b>Last Commit:</b></p>
        <p>${env.GIT_COMMIT}</p>
        <p><b>Branch:</b> ${env.GIT_BRANCH}</p>

        <p><b>Build log is attached.</b></p>

        <p><b>Please check the logs and take necessary actions.</b></p>

        <p><b>Extent Report (if available):</b> <a href="http://localhost:8080/job/SampleProject_pipeline/lastSuccessfulBuild/artifact/src/test/resources/ExtentReports/">Click here</a></p>

        <p>Best regards,</p>
        <p><b>Automation Team</b></p>
        </body>
        </html>
        """,
        mimeType: 'text/html',
        attachLog: true
    )
}
    }
}