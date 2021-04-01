# gobld-plugin

## Introduction

This plugin provides an interface with the GoBLD.

## Getting started


### JCasC

See [examples](src/test/resources/io.jenkins.plugins.gobld.jcasc/configuration-as-code.yml)

### Call when a job just entered the queue

Implements [onEnterBuildable](https://javadoc.jenkins.io/hudson/model/queue/QueueListener.html#onEnterBuildable-hudson.model.Queue.BuildableItem-)

Therefore ask for a Gobld node if possible.

### Call when a job just left the queue

Implements [onLeft](https://javadoc.jenkins.io/hudson/model/queue/QueueListener.html#onLeft-hudson.model.Queue.LeftItem-)

Therefore kill the assigned worker if possible.

### Queue status

If you'd like to query the build queue then:

```
http://localhost:8080/jenkins/gobld-webhook/queuePost
```

Output format:

```json
{
    "currentTime":{"date":31,"hours":17,"seconds":6,"month":2,"timezoneOffset":-60,"year":121,"minutes":38,"time":1617208686627,"day":3},
    "items":[
        {
            "blocked":false,
            "taskURL":"job/a/",
            "why":"Waiting for next available executor",
            "id":1,
            "labelExpression":"master",
            "url":"queue/item/1/"
        }
    ]
}
```
