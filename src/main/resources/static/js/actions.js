function showForm() {
	document.querySelector('.edit-form').style.display = 'block';
	document.querySelector('.underlying-with-opacity').style.display = 'block';
	document.querySelector('body').style.overflowY = "hidden";
}

function hideform() {
	document.querySelector('.edit-form').style.display = 'none';
	document.querySelector('.underlying-with-opacity').style.display = 'none';
	document.querySelector('body').style.overflowY = "visible";
}

function showOrder(id){
	console.log("details"+id)
	document.getElementById("details"+id).style.display = 'block';
	document.querySelector('.underlying-with-opacity').style.display = 'block';
	document.querySelector('body').style.overflowY = "hidden";
}

function hideOrder(id) {
	document.getElementById("details"+id).style.display = 'none';
	document.querySelector('.underlying-with-opacity').style.display = 'none';
	document.querySelector('body').style.overflowY = "visible";
}