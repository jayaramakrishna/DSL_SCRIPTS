folder('HPEPROJECT') {
    description('hpsim project foloder created')
}
freeStyleJob('HPEPROJECT/compile') {
    logRotator(-1, 10)
    scm {
        github('jayaramakrishna/myweb', 'master')
    }
    
    steps {
        maven('clean compile')
    }
  publishers {
        downstream('HPEPROJECT/test', 'SUCCESS')
    }
    
}
mavenJob('HPEPROJECT/test') {
    logRotator(-1, 10)
    scm {
        github('jayaramakrishna/myweb', 'master')
    }
    
    goals('clean test')
  publishers {
        downstream('HPEPROJECT/sonar', 'SUCCESS')
    }
    
}
mavenJob('HPEPROJECT/sonar') {
    logRotator(-1, 10)
    scm {
        github('jayaramakrishna/myweb', 'master')
    }
    
    goals('clean sonar:sonar')
  publishers {
        downstream('HPEPROJECT/nexus', 'SUCCESS')
    }
    
 }
mavenJob('HPEPROJECT/nexus') {
    logRotator(-1, 10)
    scm {
        github('jayaramakrishna/myweb', 'master')
    }
    
    goals('clean deploy')
}
buildPipelineView('HPEPROJECT/build-pipeline') {
    filterBuildQueue()
    filterExecutors()
    title('HPEPROJECT CI Pipeline')
    displayedBuilds(5)
    selectedJob('HPEPROJECT/compile')
    alwaysAllowManualTrigger()
    showPipelineParameters()
    refreshFrequency(60)
}
