
This folder contains two stored procedures from my project on Hotel Reservation System.

1. sp_HotelListSelectPr - This stored procedure is used every time the user searches for
		 	a list of hotels by providing a state and city information, and 
			checking or unchecking a checkBox which displays all hotels when 
			unchecked, and only hotels with available rooms when checked.


2. sp_ReviewInsertPr - This stored procedure is executed when any user writes a review for 
		    a hotel he stayed in. The procedure takes the hotelID, userID and
		    the comments that the user entered in, and inserts them in a table
		    called Review.
		     