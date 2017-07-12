<style>
/*reset style  */
/* http://meyerweb.com/eric/tools/css/reset/ 
   v2.0 | 20110126
   License: none (public domain)
*/

html, body, div, span, applet, object, iframe,
h1, h2, h3, h4, h5, h6, p, blockquote, pre,
a, abbr, acronym, address, big, cite, code,
del, dfn, em, img, ins, kbd, q, s, samp,
small, strike, strong, sub, sup, tt, var,
b, u, i, center,
dl, dt, dd, ol, ul, li,
fieldset, form, label, legend,
table, caption, tbody, tfoot, thead, tr, th, td,
article, aside, canvas, details, embed, 
figure, figcaption, footer, header, hgroup, 
menu, nav, output, ruby, section, summary,
time, mark, audio, video {
	margin: 0;
	padding: 0;
	border: 0;
	font-size: 100%;
	font: inherit;
	vertical-align: baseline;
}
/* HTML5 display-role reset for older browsers */
article, aside, details, figcaption, figure, 
footer, header, hgroup, menu, nav, section {
	display: block;
}
body {
	line-height: 1;
}
ol, ul {
	list-style: none;
}
blockquote, q {
	quotes: none;
}
blockquote:before, blockquote:after,
q:before, q:after {
	content: '';
	content: none;
}
table {
	border-collapse: collapse;
	border-spacing: 0;
}


/* customized style */

body { 
    width:100%;
    height:100%;
    background-color: lug
}
* {
    box-sizing: border-box;
}

div{
  display: block;
}

ol, ul {
    list-style: none;
}

a{
    text-decoration: none;
}

input{
    font-size: 16px;
}

.formBtn {
    background-color:#041633; 
    color: white; 
    border: 1px solid rgb(220,220,220);
    padding: 5px 30px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 20px 10px;
    -webkit-transition-duration: 0.4s; /* Safari */
    transition-duration: 0.4s;
    cursor: pointer;
}

.formBtn:hover {
    background-color: rgb(32, 60, 104);
    box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);
    color: white;
}

#wrapper{
    width: 100%;
}

.container_bg{
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    overflow: hidden;
    z-index: -100;
}

.container_bg img { 
    position: absolute;
    top: 50%;
    left: 50%;
    width: auto;
    height: auto;
    min-width: 100%;
    min-height: 100%;
    -webkit-transform: translate(-50%, -50%);
    -moz-transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
}

.panel-center{
    position: absolute;
    min-width:30%;
    min-width:24em;
    top:50%;
    left:50%;
    transform: translate(-50%, -50%);
    display: flex;
}

.panel-center1{
    position: absolute;
    min-width:20em;
    min-width:30%;
    top:-25%;
    left:47%;
    transform: translate(-50%, -50%);
    display: flex;
}

.loginBtn{
    flex: 50%;
    position:relative;
    margin:2%;
    border: 1px solid white; 
    border-radius: 6px;
}

.loginBtn input{
    position:relative;
    width:100%;
    height:100%;
    border-radius: 6px;
	border: none;
	color: #FFFFFF;
	background-color:#041633;
	text-align: center;
	font-size: 18px;
	padding: 20px 20px;
	-webkit-transition-duration: 0.4s; /* Safari */
    transition-duration: 0.4s;
}

.loginBtn input:hover {
    box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24), 0 17px 50px 0 rgba(0,0,0,0.19);
    background-color: rgb(32, 60, 104);
    color: white;
}

#loginForm {
    text-align: center;
    color: white;
    display:block;
    border: 1px solid white;
    line-height: 2em;
    padding: 3% 5%;
    transform: translate(-50%, -50%);
    box-shadow: 0 20px 32px 0 rgba(0,0,0,0.4), 0 12px 40px 0 rgba(0,0,0,0.38);
}

#loginForm h2 {
   font-size:26px;
   margin-bottom: 1em;
}

#loginForm p{
    display: block;
    position:relative;
    text-align: left;
    font-size: 20px;
}

#loginForm p input{
    display: inline-block;
    float:right;
    width:50%;
}



#loginForm .formBtn{
     display: inline-block;
     position:relative;
     float: left;
     width: 8em;
     left: 30%;
}

#header {
    top: 0;
    left:0;
    width:100%;
    overflow: hidden;
    background-color: #041633 /**blue*/;
    position: fixed;
    min-height:2.5em;
    z-index: 1000;
    font-size: 24px;
}

#header .logo{
    float: left;
    display: inline-block;
    margin-top: 15px;
    margin-bottom:15px;
    color: white;
    margin-left: 3%;
}

.showLogin{
    float: right;
    display: inline-block;
    margin-top:15px;
    margin-bottom:15px;
    color: white;
    margin-right: 3%;
}

#sideNav-bg{
    position:fixed;
    top:0;
    left:0;
    height:100%;
    background-color:rgb(250,250,250);
    border-right: 1px solid rgb(220,220,220);
    z-index: 500;
     
}

#sideNav-bar {
    margin-top: 4em;
    margin-left: 0;
    z-index: 500;
    position: relative;
    width: 250px;
    height: 100%;
    float: left;
    clear:both;
    
}

.nav-item {
    position:relative;
    display:block;
    text-align: center;
    padding:12px;
    border-bottom: 1px solid rgb(220,220,220);
    min-height:6%;
}

main {
	margin-left: 250px;
	margin-bottom: 30px;
	right:0;
    display: block;
    position: relative;
}

/* center form in the left main container */
main .center-form{
    text-align: center;
    position: relative;
    min-width: 350px;
    min-height: 10%;
    padding:30px 35px;
    margin-top: 150px;
    margin-left: 25%;
    margin-right: 35%;
    margin-bottom: 20%;
    border: 2px solid grey;
    
    box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);
}

main .center-form h2 {
   font-size:26px;
   margin-bottom: 1em;
}

main .center-form p{
    display: block;
    position:relative;
    text-align: left;
    font-size: 20px;
    line-height: 150%;
}

main .center-form p input {
    position:relative;
    float: right;
    width: 40%;
}

main .panel{
    display:block;
    position:relative;
    background-color:rgba(232,228,196,1);
    margin-top: 5em;
    margin-left: 5%;
    margin-right: 5%;
    margin-bottom: 10%;
    padding:20px 25px;
    line-height: 120%;
}

main .panel h2{
    font-size: 22px;
    margin-bottom: 10px;
    
}

main .panel-profile{
    padding: 20px 25px;
    min-height: 50px;
    margin-bottom: 20px;
    border: 1px solid grey;
    background-color: white;
}

main .operation{
    display: flex;
    flex-wrap: ltr;
    min-height: 50px;
    margin-bottom: 20px;
}

.operation .formBtn{
   position:relative;
   min-height: 2em;
   min-width:  6em;
   text-align: left;
   font-size: 18px;
   margin: 5px;
}

.operation .formBtn a{
   color:white;
}

main .content p{
    font-size: 20px;
    line-height: 1.5em; 
}

main .panel-history{
    padding: 20px 25px;
    min-height: 50px;
    margin-bottom: 20px;
    border: 1px solid grey;
    background-color: white;
    box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);
}

.panel-history table{
    table-layout: auto;
    position:relative;
    border-collapse: separate;
    border-spacing: 2px;
    width: 100%;
    overflow: hidden;
    background: #FFF;
    color: #024457;
    display: flex;
}

.panel-history table tr{
    text-align: right;
}

.date{
    margin-bottom: 1em;
}

/* .panel-history .table tbody {
    display: table-row-group;
    vertical-align: middle;
    border-color: inherit;
} */

.panel-history .table th{
    position: relative;
    display: table-cell;
    min-width: 9em;
    text-align: center;
    background-color: #041633;
    color: white;
    padding: 10px 5px;
    border-right: 2px solid lightgrey;
}


.table tr:nth-child(odd) {
    background-color: rgba(232,228,196,1);
}

.panel-history table td {
    position:relative;
    font-size: 20px;
    padding: 5px 10px;
    border-left: 1px solid lightgrey;
    border-right: 1px solid lightgrey;
    border-spacing: 0px;
}

.panel-history .table .formBtn{
    display: block;
    position: relative;
    min-width: 2em;
    padding: 5px 10px;
    margin:0;
    margin-left: 25%;
}

#researchFundHis{
    border:none;
    box-shadow: none;
}

#researchFundPanel{
    box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);
    padding: 20px 0;
    padding-left: 30px;
}

main #researchFundByName{
   position:relative;
   min-height: 3em;
   min-width:  4em;
   font-size: 18px;
   margin: 0;
}

main #researchFundByName #fundName{
   padding: 8px;
   text-align:center;
   background-color: white;
   min-width:4em;
   min-hight: 1em;
   border:none;
}

main #researchFundByName a{
   color:#041633;
}

main #researchFundByName #fundName:hover {
   box-shadow:none;
   background-color:rgb(230,230,195);
}



.panel-left {
    position:relative;
    display: inline-block;
    width: 45%;
    padding: 2% 5%;
    min-height: 120px;
    margin-top: 3%;
    margin-bottom: 10%;
    border: 3px solid #041633;
    background-color: white;
    text-align: center;
    float: left;
    box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);
}

.panel-left .form-group {
    margin: 10px 10px;
    text-align: left;
}

.panel-left .form-group > label {
    font-size: 18px;
    line-height: 150%;
}

.form-group input{
    float: right;
    width: 40%;
    font-size: 14;
}

.panel h2{
    margin: 20px;
    font-size:26px;
    font-weight:bold;
}

.panel p {
    margin: 20px;
}



}
/* .btn{
    display: block;
    margin-top: 30px;
    margin-bottom: 20px;
    margin-left: 35%;
    text-align: center;
    font-size: 16px;
    width: 30%;
    height: 1.7em;
    position: relative;
    background-color: lightgrey;
    border: none;

} */

.panel-right {
    display: inline-block;
    width: 45%;
    padding: 30px 25px;
    min-height: 120px;
    margin-top: 3%;
    margin-bottom: 10%;
    border: 3px solid #041633;
    background-color: white;
    text-align: center;
    float: right;
    box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);
}



footer {
  display:block;
  width:100%;
  position: fixed;
  padding: 10px;
  right: 0;
  bottom: 0;
  background-color: #efefef;
  text-align: center;
  z-index: 1000;
  line-height:120%;
}

footer ul li{
    position:relative;
    width:60%;
    margin-left:20%;
    display: flex
}


footer ul li a{
    flex: 1;
} 


@media (max-width: 1100px) {
    .container_bg {
        background: url('https://s3-us-west-2.amazonaws.com/s.cdpn.io/4273/polina.jpg') center center / cover no-repeat;
    }
    
/*     main .center-form{
	    width: 50%;
	    min-height: 10%;
	    padding:10px 15px;
	    margin-left: 20%;
	    margin-right: 20%;
	    margin-bottom: 20%;
    } */
}

@media (max-width: 900px) {
    .container_bg {
        background: url('https://s3-us-west-2.amazonaws.com/s.cdpn.io/4273/polina.jpg') center center / cover no-repeat;
    }
    
/*     main .center-form{
	    width: 70%;
	    min-height: 10%;
	    padding:10px 15px;
	    margin-left: 15%;
	    margin-right: 15%;
	    margin-bottom: 20%;
    } */
}



/* 
#main-no-nav{
    width: 100%;
    margin: 100px auto;
    max-width: 1200px;
    background-color: white;
    text-align: center;
}
.notification{
    width: 100%;
    height:100%;
    text-align: center;
    padding-top: 10%;
}


.search-bar{
    padding: 20px 10px;
}
.search-result{
    padding: 20px 25px;
    min-height: 50px;
    margin-bottom: 20px;
    border: 1px solid grey;
    background-color: white;

}


.notification{
    left: 40%;
    float: left;
}


#center-panel-2{
    top: 50%;
    left: 60%;
    width: 20em;
    min-height:18em;
    margin-top: -9em; 
    margin-right: -15em; 
    border: 2px solid grey;
    background-color: rgba(220,220,220,0.8);
    position:fixed;
    text-align: center;
    font-size: 18px;
}

#center-panel-2 table {
    padding: 10px;
    margin: 0 auto;
    border-collapse: collapse;
}

#center-panel-2 table td{
    padding-top: .5em;
    padding-bottom: .5em;
}

#buttom{
    vertical-align: middle;
}
/* input {
    font-size: 18px;
} */
 */
*/ */
 */</style>