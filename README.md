# TMJRock
<b>Overview</b><br>
This is about javascript library which provides the features of Ajax calls and some useful UI components used for client side programming.<br>
<b>Ajax call</b><br>
It include placing ajax calls through function in which user passes necessary information by wrapping it in JSON format.<br>
Get type request Example<br>
  Get type request with data example<br> 
  Post type Request Example<br>
<b>Here Info include</b>
<ul>
<li> URL: for which one have to place ajax calls </li>
  <li>MethodType: it could be POST/GET type </li>
  <li>Success: function execute on successful completion of ajax call.</li>
  <li>failure: function execute on failure of ajax call.</li>
</ul>
<b>format of placing calls</b><br>
$$$.ajax({
"url": "requestUrl",
"methodType": "GET",
"success": function(responseData){
},
"failure": function(){
}
});

