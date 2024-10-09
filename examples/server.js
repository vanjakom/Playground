var http = require('http');
var fs = require('fs');

http.createServer(function (req, res) {
  	console.log('request: ' + req.url);
	
	if (req.url == "/page.html") {
		fs.readFile("page.html", function(err, data) {  
			if (err) {  
				res.writeHead(500, {"Content-Type": "text/plain"});  
				res.write(err + "\n");
				res.end();
	                	return;
			}  

			res.writeHead(200, {'Content-Type': 'text/html'});
			res.write(data);
			res.end();
		});
        } else if (req.url == "/jquery.js") {
		fs.readFile("jquery.js", function(err, data) {  
			if (err) {  
				res.writeHead(500, {"Content-Type": "text/plain"});  
				res.write(err + "\n");
				res.end();
	                	return;
			}  

			res.writeHead(200, {'Content-Type': 'text/html'});
			res.write(data);
			res.end();
		});
	} else if (req.url == "/status") {
  		res.writeHead(200, {'Content-Type': 'text/json'});
  		res.end('{"message":"Hello World","status":"ok","url":"' + req.url + '"}');
  	} else if (req.url == "/test") {
		res.writeHead(200, {"Content-Type": "text/plain"});
		res.end("Test");
	} else {
		res.writeHead(500, {"Content-Type": "text/plain"});
		res.end("Unknown request: " + req.url);
	}
}).listen(1337);

console.log('Server running at http://localhost:1337/');

