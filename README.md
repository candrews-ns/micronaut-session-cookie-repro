Repro App for https://github.com/micronaut-projects/micronaut-core/issues/3936
==============================================================================
```
$ ./gradlew run
```

Working session cookie:

```
$ curl -v http://localhost:8080/no-bug
*   Trying 127.0.0.1:8080...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> GET /no-bug HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.74.0
> Accept: */*
>
* Mark bundle as not supporting multiuse
< HTTP/1.1 200 OK
< set-cookie: SESSION=YWM1OTU2ODQtNmNiMC00MzJjLWE0ZWEtNzQyN2RiNmEwZDlk; Path=/; HTTPOnly
< Date: Tue, 12 Jan 2021 16:53:03 GMT
< content-type: application/json
< content-length: 11
< connection: keep-alive
<
* Connection #0 to host localhost left intact
No bug here⏎
```

Bad session cookie:

(note that Path and HTTPOnly are missing)

```
$ curl -v http://localhost:8080/bug
*   Trying 127.0.0.1:8080...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> GET /bug HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.74.0
> Accept: */*
>
* Mark bundle as not supporting multiuse
< HTTP/1.1 200 OK
< Date: Tue, 12 Jan 2021 16:54:16 GMT
< content-type: text/plain
< content-length: 10
< set-cookie: SESSION=M2NiZjA1OGItODE2Ny00OGU5LTgwMjUtNTM4NDUwOTQyYmY0
< connection: close
<
* Closing connection 0
Hello foo!⏎
```