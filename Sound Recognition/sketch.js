var mic;
function setup(){
	createCanvas(3000,1000);
  mic = new p5.AudioIn();

  mic.start();
}

function draw() {
background(0);

	
  var vol=mic.getLevel();

  
var r = random(255);
var g = random(255);
var b = random(255);
console.log(vol);
if(vol*50000>500)
background(r,g,b)


}