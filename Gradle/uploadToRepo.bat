@echo off & setlocal DisableDelayedExpansion
set jarname=aola-alappif

Title %~n0
Mode 120,20 & Color 0E
echo Welcome,%username%, you're going to upload a newer version of '%jarname%.jar' to 'http://repo.bt'
set /p version=please enter jar version to upload:
echo.
echo "%username%" is going to upload "%jarname%.%version%.jar" to "http://repo.bt"
Call:InputPassword "Enter Your Password To UPLOAD" pwd
setlocal EnableDelayedExpansion
Cls
echo compiling and uploading '!jarname!.!version!.jar'
gradle publishMavenPublicationToMavenRepository -Pun=!username! -Pversion=!version! -Ppwd=!pwd!
pause
::***********************************
:InputPassword
set "psCommand=powershell -Command "$pword = read-host '%1' -AsSecureString ; ^
    $BSTR=[System.Runtime.InteropServices.Marshal]::SecureStringToBSTR($pword); ^
      [System.Runtime.InteropServices.Marshal]::PtrToStringAuto($BSTR)""
        for /f "usebackq delims=" %%p in (`%psCommand%`) do set %2=%%p
)
goto :eof     
::***********************************