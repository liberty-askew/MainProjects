200024105  5.11.2020
For CS5001-p3-networking/src Additional Enhancements

LOGGING-------------------------------------------------------------------------
LogHandler object takes care of updating the log.txt file for each connection
request. Some sample outputs can be seen below.

Command Line:
curl -s GET localhost:12345/page3.html

Browser:
http://localhost:12345/page3.html

Log Example:

TIME:2020-11-06 08:53:40
PORT:12345
REQUEST TYPE:GET
FILEPATH:../www/page3.html
HEAD:
HTTP/1.1 200 OK
Content-Type: text/html
Content-Length: 1144

CONTENT:
// output in every case except image where "NA - image file." is used instead as
//  image cannot be represented in txt file.


BINARY IMAGE HANDLER -----------------------------------------------------------
For locally stored gif, png & jpg files.

Command Line:
curl -s GET localhost:12345/extra/earth.gif

Browser:
http://localhost:12345/extra/earth.gif

HEAD:
HTTP/1.1 200 OK
Content-Type: invalid
Content-Length: 363021

CONTENT:
N/A - image file.

Sample Files:
- ..www/extra/earth.gif
- ..www/extra/joke.png
- ..www/another/beer.jpg
- ..www/tp_it.jpg

DELETE--------------------------------------------------------------------------
Additional HTTP request which deletes a file from the local directory. Running
twice will result in a 404 Not Found error. Example below delete index.html.

Command Line:
curl -X DELETE localhost:12345/index.html

Output:
<html>
<body>
<h1>File Deleted</h1>
</body>
</html>



MULTITHREADING------------------------------------------------------------------
Executor class has been used to implement multithreading in this file.







