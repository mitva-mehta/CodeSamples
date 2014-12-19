
/****** Object:  StoredProcedure [dbo].[sp_HotelListSelectPr]    Script Date: 10/5/2014 2:44:55 PM ******/
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'sp_HotelListSelectPr')
DROP PROCEDURE [dbo].[sp_HotelListSelectPr]
GO

/****** Object:  StoredProcedure [dbo].[sp_HotelListSelectPr]    Script Date: 10/5/2014 2:44:55 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE PROCEDURE [dbo].[sp_HotelListSelectPr]
(
	@Checkbox NVARCHAR(10),
	@StateCode NVARCHAR(10),
	@CityName NVARCHAR(100)
)
AS

SET NOCOUNT ON
DECLARE @ReturnStatus INT

SELECT LocationInfo.StateCode, LocationInfo.CityName, HotelInfo.HotelName, BranchInfo.Address,
       RoomAvailability.IsAvailable, HotelInfo.HotelID, HotelInfo.Description, Room.Type
       CASE WHEN RoomAvailability.IsAvailable = ‘Yes’
	    THEN RoomAvailability.NumOfRooms
	    ELSE ‘Fully booked’ 
       END AS RoomsAvailable
					 
       FROM LocationInfo INNER JOIN BranchInfo ON LocationInfo.locID = BranchInfo.locationID
                         INNER JOIN HotelInfo ON BranchInfo.HotelID = HotelInfo.HotelID
                         INNER JOIN Room ON Room.BranchID = BranchInfo.BranchID
                         LEFT JOIN RoomAvailability ON RoomAvailability.roomID = Room.roomID

       WHERE LocationInfo.StateCode = @StateCode
       AND LocationInfo.CityName = @CityName
       AND (@Checkbox IS NULL OR RoomAvailability.IsAvailable = ‘Yes’) 
	-- if @checkbox is null (show all hotels), this clause is ignored
                
       ORDER BY HotelInfo.HotelName

SELECT @ReturnStatus = ISNULL(@ReturnStatus, @@ERROR)            
   IF (@ReturnStatus <> 0)            
    BEGIN            
        RETURN (@ReturnStatus)            
   END


GO

