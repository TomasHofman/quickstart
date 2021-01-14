# Environment setup

* Create two distinct EAP clusters, two nodes each.
* Deploy `ejb-remote-server-side.jar` to the first cluster.
* Deploy `ejb-remote-server-side.jar` to the second cluster with name `ejb-remote-server-side2.jar`.
* Edit `RemoteEJBClient` class - change URIs of all cluster nodes according to your environment.
* Run `RemoteEJBClient` class. It invokes a bean twice on first cluster and then repeats on the second cluster. 

I can see following output. See lines starting with "INFO: CustomDeploymentNodeSelector:" and "INFO: CustomClusterNodeSelector:".

```
/usr/lib/jvm/java-11-openjdk/bin/java -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:58433,suspend=y,server=n -javaagent:/opt/idea/plugins/java/lib/rt/debugger-agent.jar -Dfile.encoding=UTF-8 -classpath /home/thofman/Projects/wildfly-quickstart/ejb-remote/client/target/classes:/home/thofman/.m2/repository/org/jboss/jboss-ejb-client/4.0.33.Final/jboss-ejb-client-4.0.33.Final.jar:/home/thofman/.m2/repository/org/jboss/marshalling/jboss-marshalling/2.0.9.Final/jboss-marshalling-2.0.9.Final.jar:/home/thofman/.m2/repository/org/jboss/marshalling/jboss-marshalling-river/2.0.9.Final/jboss-marshalling-river-2.0.9.Final.jar:/home/thofman/.m2/repository/org/jboss/xnio/xnio-api/3.8.1.Final/xnio-api-3.8.1.Final.jar:/home/thofman/.m2/repository/org/jboss/threads/jboss-threads/2.3.3.Final/jboss-threads-2.3.3.Final.jar:/home/thofman/.m2/repository/org/jboss/xnio/xnio-nio/3.8.1.Final/xnio-nio-3.8.1.Final.jar:/home/thofman/.m2/repository/org/jboss/remoting/jboss-remoting/5.0.18.Final/jboss-remoting-5.0.18.Final.jar:/home/thofman/.m2/repository/org/jboss/logging/jboss-logging/3.4.1.Final/jboss-logging-3.4.1.Final.jar:/home/thofman/.m2/repository/org/jboss/spec/javax/ejb/jboss-ejb-api_3.2_spec/2.0.0.Final/jboss-ejb-api_3.2_spec-2.0.0.Final.jar:/home/thofman/.m2/repository/org/wildfly/client/wildfly-client-config/1.0.1.Final/wildfly-client-config-1.0.1.Final.jar:/home/thofman/.m2/repository/org/wildfly/common/wildfly-common/1.5.4.Final/wildfly-common-1.5.4.Final.jar:/home/thofman/.m2/repository/org/wildfly/discovery/wildfly-discovery-client/1.2.1.Final/wildfly-discovery-client-1.2.1.Final.jar:/home/thofman/.m2/repository/org/wildfly/security/wildfly-elytron/1.12.1.Final/wildfly-elytron-1.12.1.Final.jar:/home/thofman/.m2/repository/org/wildfly/transaction/wildfly-transaction-client/1.1.11.Final/wildfly-transaction-client-1.1.11.Final.jar:/home/thofman/.m2/repository/org/wildfly/wildfly-naming-client/1.0.13.Final/wildfly-naming-client-1.0.13.Final.jar:/home/thofman/.m2/repository/org/jboss/ejb3/jboss-ejb3-ext-api/2.3.0.Final/jboss-ejb3-ext-api-2.3.0.Final.jar:/home/thofman/.m2/repository/org/jboss/spec/javax/resource/jboss-connector-api_1.7_spec/2.0.0.Final/jboss-connector-api_1.7_spec-2.0.0.Final.jar:/home/thofman/.m2/repository/org/jboss/spec/javax/transaction/jboss-transaction-api_1.3_spec/2.0.0.Final/jboss-transaction-api_1.3_spec-2.0.0.Final.jar:/home/thofman/.m2/repository/org/wildfly/wildfly-http-client/wildfly-http-ejb-client/1.0.21.Final/wildfly-http-ejb-client-1.0.21.Final.jar:/home/thofman/.m2/repository/io/undertow/undertow-core/2.1.3.Final/undertow-core-2.1.3.Final.jar:/home/thofman/.m2/repository/org/wildfly/wildfly-http-client/wildfly-http-client-common/1.0.21.Final/wildfly-http-client-common-1.0.21.Final.jar:/home/thofman/.m2/repository/org/wildfly/wildfly-http-client/wildfly-http-naming-client/1.0.21.Final/wildfly-http-naming-client-1.0.21.Final.jar:/home/thofman/.m2/repository/org/wildfly/wildfly-http-client/wildfly-http-transaction-client/1.0.21.Final/wildfly-http-transaction-client-1.0.21.Final.jar:/home/thofman/Projects/wildfly-quickstart/ejb-remote/server-side/target/classes:/opt/idea-IC-202.6397.94/lib/idea_rt.jar org.jboss.as.quickstarts.ejb.remote.client.RemoteEJBClient
Connected to the target VM, address: '127.0.0.1:58433', transport: 'socket'
OpenJDK 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
Jan 14, 2021 7:05:57 PM org.xnio.Xnio <clinit>
INFO: XNIO version 3.8.1.Final
Jan 14, 2021 7:05:57 PM org.xnio.nio.NioXnio <clinit>
INFO: XNIO NIO Implementation Version 3.8.1.Final
Jan 14, 2021 7:05:57 PM org.jboss.threads.Version <clinit>
INFO: JBoss Threads version 2.3.3.Final
Jan 14, 2021 7:05:57 PM org.jboss.remoting3.EndpointImpl <clinit>
INFO: JBoss Remoting version 5.0.18.Final
Jan 14, 2021 7:05:58 PM org.wildfly.naming.client.Version <clinit>
INFO: WildFly Naming version 1.0.13.Final
Jan 14, 2021 7:05:58 PM org.wildfly.security.Version <clinit>
INFO: ELY00001: WildFly Elytron version 1.12.1.Final
Jan 14, 2021 7:05:58 PM org.jboss.ejb.client.EJBClient <clinit>
INFO: JBoss EJB Client version 4.0.33.Final
Obtained a remote stateless calculator for invocation
Adding 204 and 340 via the remote stateless calculator deployed on the server
Jan 14, 2021 7:06:00 PM org.jboss.as.quickstarts.ejb.remote.client.RemoteEJBClient$CustomDeploymentNodeSelector selectNode
INFO: CustomDeploymentNodeSelector: Eligible nodes: master:server-two, master:server-one
Remote calculator returned sum = 544
Subtracting 2332 from 3434 via the remote stateless calculator deployed on the server
Jan 14, 2021 7:06:01 PM org.jboss.as.quickstarts.ejb.remote.client.RemoteEJBClient$CustomClusterNodeSelector selectNode
INFO: CustomClusterNodeSelector: Connected nodes: master:server-two, master:server-one
Remote calculator returned difference = 1102
Obtained a remote stateless calculator for invocation
Adding 204 and 340 via the remote stateless calculator deployed on the server
Jan 14, 2021 7:06:02 PM org.jboss.as.quickstarts.ejb.remote.client.RemoteEJBClient$CustomDeploymentNodeSelector selectNode
INFO: CustomDeploymentNodeSelector: Eligible nodes: master:server-four, master:server-three
Remote calculator returned sum = 544
Subtracting 2332 from 3434 via the remote stateless calculator deployed on the server
Jan 14, 2021 7:06:03 PM org.jboss.as.quickstarts.ejb.remote.client.RemoteEJBClient$CustomClusterNodeSelector selectNode
INFO: CustomClusterNodeSelector: Connected nodes: master:server-four, master:server-three
Remote calculator returned difference = 1102
Disconnected from the target VM, address: '127.0.0.1:58433', transport: 'socket'

Process finished with exit code 0
```