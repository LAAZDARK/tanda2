<?php 
$hostname='localhost';
$database='tanda';//quiz
$username='root';//root
$password='';

$conexion = mysqli_connect($hostname,$username,$password,$database);
if ($conexion->connect_errno){
	echo "Web en manteniemiento erro 404 :)";
}

 ?>