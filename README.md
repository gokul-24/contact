**Bitespeed Backend Task: Identity Reconciliation using Springboot**

**Website url** : **Website url** : [https://contact-dmel.onrender.com/all](https://contact-dmel.onrender.com/all)

There are 2 endpoints:
1. /all       -  Will return all the rows in the table.
2. /indentify -  Will accept HTTP POST requests with email and phoneNumber and process it to return a JSON object.

**HOW TO TEST**
Send a HTTP POST request with JSON body of the following format (format is as per the task) from POSTMAN or any such services: 

Sample Input : 


![sample_input](https://github.com/gokul-24/contact/assets/71783639/180815c8-8908-431a-b68b-48b0dcd70511)

Sample Output:


![sample_output](https://github.com/gokul-24/contact/assets/71783639/658b47bd-aa31-43cd-9a82-ca06f69c5579)


**POSSIBLE TEST SCENARIOS IDENTIFIED**
1. Email or Phone of incoming request doesn't match with any other request - we add a new row into the table with LinkPrecedence primary
2. If email and phone matches and both are of same contact - we retrieve the existing data and send response.
3. If email or phone only one of them matches - if email or phone is null, we just retrieve existing data else if email or phone is unique and other one is matching
   we will insert new row with LinkPrecedence secondary and add LinkedId.
4. If email and phone matches but they are of different contacts - Find the first occurring primary contact among the 2 and make the other one secondary and update the LinkedId.
