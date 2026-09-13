@echo off
cd /d C:\Users\Mariana\Desktop\DealRank\src

echo Setting PYTHONPATH...
set PYTHONPATH=C:\Users\Mariana\Desktop\DealRank\src

echo Initiating webservice...
python -m webservice.webservice

pause
