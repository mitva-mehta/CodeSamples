
/****** Object:  StoredProcedure [dbo].[sp_ReviewInsertPr]    Script Date: 10/5/2014 2:45:08 PM ******/
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'sp_ReviewInsertPr')
DROP PROCEDURE [dbo].[sp_ReviewInsertPr]
GO

/****** Object:  StoredProcedure [dbo].[sp_ReviewInsertPr]    Script Date: 10/5/2014 2:45:08 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[sp_ReviewInsertPr]
(
	@HotelID uniqueidentifier,
	@UserID uniqueidentifier,
	@Feedback nvarchar(max)
)
AS

SET NOCOUNT ON
DECLARE @ReturnStatus INT
DECLARE @RC uniqueidentifier

IF @Feedback is not null AND @Feedback<>''
BEGIN
	IF @HotelID is not null AND @UserID is not null
	BEGIN
	SELECT @RC = (SELECT ReviewID FROM Review WHERE HotelID = @HotelID AND UserID = @UserID)  
	   IF @RC IS NULL
			BEGIN 
				DECLARE @ReviewID uniqueidentifier = NEWID()
				INSERT INTO Review
				(
					ReviewID,
					HotelID,
					UserID,
					Feedback
				)
				VALUES   		
				(
					@ReviewID,
					@HotelID,
					@UserID,
					@Feedback
				)

				SELECT @ReturnStatus = ISNULL(@ReturnStatus, @@ERROR)            
				   IF (@ReturnStatus <> 0)            
					 BEGIN            
						RETURN (@ReturnStatus)            
				   END

				SELECT @ReviewID
			END
		ELSE
			UPDATE Review
			SET Feedback = @Feedback
			WHERE ReviewID = @RC
		
			SELECT @RC
	END
END
GO

