var journey = require('journey');
var https = require('https');
var fs = require('fs');

var privateKey = fs.readFileSync('cert/privatekey.pem');
var certificate = fs.readFileSync('cert/certificate.pem');

var options = {
	key: privateKey,
	cert: certificate
};

var mrouter = new (journey.Router)();

mrouter.map(function() {
	this.root.bind(function(req, res) {
		res.send("Hey hey");
	});
});

https.createServer(options, function(req, res) {
	var body = "";

	req.addListener('data', function(chunk) { body += chunk });
	req.addListener('end', function() {
		mrouter.handle(req, body, function(result) {
			res.writeHead(result.status, result.headers);
			res.end(result.body);
		});
	});
}).listen(8081);



