@echo off

REM Export parameters
SET EXPORT_HOST=localhost
SET EXPORT_PORT=3306
SET EXPORT_USER=root
SET EXPORT_PASSWORD=lxbordo
SET EXPORT_DATABASE=boozeandice
SET EXPORT_FILE=boozeandice_cloudsync.sql

REM Import parameters
SET IMPORT_HOST=boozeandice-test-rds.c7l9gtvaasvy.ap-southeast-1.rds.amazonaws.com
SET IMPORT_PORT=3306
SET IMPORT_USER=admin
SET IMPORT_PASSWORD=bait-micke
SET IMPORT_DATABASE=boozeandice

REM Export SQL boozeandice_cloudsync.sql
mysqldump -h %EXPORT_HOST% -P %EXPORT_PORT% -u %EXPORT_USER% -p%EXPORT_PASSWORD% %EXPORT_DATABASE% > %EXPORT_FILE%

REM Check if export was successful
IF %ERRORLEVEL% NEQ 0 (
	echo "Export failed. Aborting import."
	exit /b
)

echo "Export completed successfully."

REM Import SQL backup
mysql -h %IMPORT_HOST% -P %IMPORT_PORT% -u %IMPORT_USER% -p%IMPORT_PASSWORD% %IMPORT_DATABASE% < %EXPORT_FILE%

REM Check if import was successful 
if %ERRORLEVEL% NEQ 0 (
	echo "Import failed."
) else (
	echo "Import completed successfully."
)

REM Clean up the exported file
del %EXPORT_FILE%
