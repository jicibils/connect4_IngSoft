var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var i =0;
var ch1,ch2,ch3,ch4,ch5,ch6,ch7,ch8,ch9,ch10;
app.get('/', function(req, res){
  res.sendFile(__dirname + '/index.html');
});

io.on('connection', function(socket){

	socket.on('publisher',function(msg){
		if (i<=9){
			i++;
			io.emit("publisher",i.toString()+':'+msg);
		}else{
			io.emit("publisher",0);
		}
	});



	socket.on('waitingSuscriber',function(msg){
		if (i>0){
			io.emit('waitingSuscriber',msg);
		}else{
			io.emit('waitingSuscriber',0);
		}
	});


	socket.on('suscriber',function(msg){
		if (i>0){
			io.emit('suscriber',msg);
		}else{
			io.emit('suscriber',0);
		}
	});


	socket.on('chn1', function(msg){
    	io.emit('chn1', msg);
  	});


	socket.on('chn2', function(msg){
	    io.emit('chn2', msg);
	});


	socket.on('chn3', function(msg){
	    io.emit('chn3', msg);
	});

	socket.on('chn4', function(msg){
	    io.emit('chn4', msg);
	});

	socket.on('chn5', function(msg){
	    io.emit('chn5', msg);
	});


	socket.on('chn6', function(msg){
	    io.emit('chn6', msg);
	});

	socket.on('chn7', function(msg){
	    io.emit('chn7', msg);
	});

	socket.on('chn8', function(msg){
	    io.emit('chn8', msg);
	});

	socket.on('chn9', function(msg){
	    io.emit('chn9', msg);
	});

	socket.on('chn10', function(msg){
	    io.emit('chn10', msg);
	});


	  socket.on('chat message', function(msg){
	    io.emit('chat message', msg);
	  });
	});

	http.listen(3000, function(){
	}); 
