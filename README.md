# TMJRock
<b>Overview</b><br>
This is about javascript library which provides the features of Ajax calls and some useful UI components used for client side programming.<br>
## Ajax call
It include placing ajax calls through function in which user passes necessary information by wrapping it in JSON format.<br>
Get type request Example<br>
  Get type request with data example<br> 
  Post type Request Example<br>
### Here Info include
<ul>
<li> URL: for which one have to place ajax calls </li>
  <li>MethodType: it could be POST/GET type </li>
  <li>Success: function execute on successful completion of ajax call.</li>
  <li>failure: function execute on failure of ajax call.</li>
</ul>

### Format of placing calls

```
$$$.ajax({
"url": "requestUrl",
"methodType": "GET",
"success": function(responseData){
},
"failure": function(){
}
});
```
## Modal
<ul>
<li>This include creation of modal for the division id for which modal is required this works by placing call to modal function we have defined by providing it id. <br></li>
<li>For creating it we need to set some basic properties such as modalmask modalbackgroung header size style buttons If needed. <br></li>
<li>Here we also get to know about opening and closing of modal for division id respectively.</li>
<li>For more clearity and understanding go through its module. </li>
</ul>

### How to place call
```
function createModal1()
{
var modal=new Modal("ab");
modal.onOpen=function(){
alert("Modal with ab will be opened");
};
modal.onClose=function(){
alert("Modal with ab+ closed");
};
modal.show();
}
```
## Accordian Panel

To use the accordion panel we need to create a division in which we have to set property 'accordian=true', then inside that division, we have to create a heading and below the heading, we have to define respective information whatever we want in the new division just next to it.

### Usage
```
<body>
<div accordian="true">
<h3>Heading 1</h3>
<div>
1 whatever whatever
2 whatever whatever
</div>
<h3>Heading 2</h3>
<div>
11 Something something
22 Something something
</div>
<h3>Heading 3</h3>
<div>
111 whatever whatever
222 whatever whatever
</div>
</div>
</body>
```
## Validation 
 we provide a function that takes basic validation information in JSON format and check all the validation or add other input format as per user requirements. refer below module
 ```
 <!DOCTYPE html>
<html lang='en'>
<head>
<meta charset='utf-8'>
<title>AJAX</title>

<style>
</style>
<script> 
//TMJRock part starts here
function $$$(parameter){
alert(parameter);
return {
"isValid": function(info)
{
alert(info);
//var information=JSON.parse(info);
//alert(information);
}

};

}

//TMJRock part ends here
//TMJRock user parts starts here
function doSomething()
{
return $$$("someForm").isValid({
"nm":{
"required":true,
"max-length":20,
"error-pane":"nmErrorSection",
"errors":{
"required":"Name required",
"max-length":"Name cannot exceed 20 characters"
}
},
"ad":{
"required":true,
"error-pane":"adErrorSection",
"errors":{
"required":"Address required"
}
},
"ct":{
"required":true,
"error-pane":"ctErrorSection",
"errors":{
"required":"Select city"
}
},
"gender":{
"invalid":-1,
"error-pane":"genderErrorSection",
"errors":{
"invalid":"Select gender"
}
},
"agree":{
"required-state":true,
"display-alert":true,
"errors":{
"required":"Select I Agree checkbox"
}
}
});
}

//TMJRock user parts ends here
</script>
</head>

<body>
<h1>TMJRock Validation</h1>

<form id='someForm' action='whatever' onsubmit='return doSomething()'>
Name : <input type='text' name='nm' id='nm'>
<span id='nmErrorSection'></span><br>
Address : <textarea name='ad' id='ad'></textarea>
<span id='adErrorSection'></span><br>
Select City : <select id='ct' name='ct'> 
<option value='-1'>Select city</option>
<option value='1'>Ujjain</option>
<option value='2'>Dewas</option>
<option value='3'>Indore</option>
</select><span id='ctErrorSection'></span><br>
Gender :&nbsp;&nbsp;&nbsp;
Male <input type='radio' name='gender' id='ml' value='M'>
&nbsp;&nbsp;&nbsp;&nbsp;
Female <input type='radio' name='gender' id='fl' value='F'>
&nbsp;&nbsp;&nbsp;
<span id='genderErrorSection'></span><br>
Is Indian ?  <input type='checkbox' name='agree' id='ag' value='Y'>
<br>
<Button type='submit'>Register</button>
</form>

</body>
</html>
 ```

## Note :
first of all please go through the examples for clarification you would find the way functions are working and how we are placing calls according to function requirement.<br>
So if one can want to use the facility of modal and accordian simultaneously refer the module in which i give you explanation how we can do that<br>
Apart from this one of the key feature of library is grid for now it is demonstrated by an example of student grid along with thier details go through the module to understand what it is about and how it works.


