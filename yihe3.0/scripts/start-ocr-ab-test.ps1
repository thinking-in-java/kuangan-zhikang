param()
$ErrorActionPreference='Stop'
$taskProjectRoot=Split-Path -Parent $PSScriptRoot
$taskWorkspaceRoot=Split-Path -Parent (Split-Path -Parent $taskProjectRoot)
$taskStarter=Join-Path $taskWorkspaceRoot '.tools/start-ehr.ps1'
$taskToken='F:/upload/.config/ocr-worker.synthetic.token'
if(!(Test-Path -LiteralPath $taskStarter)){throw 'Workspace start-ehr.ps1 was not found'}
if(!(Test-Path -LiteralPath $taskToken)){throw 'The dedicated synthetic Worker Token has not been provisioned'}
if($env:EHR_OCR_WORKER_TOKEN){throw 'Clear inherited EHR_OCR_WORKER_TOKEN before starting the isolated test service'}
if(Get-NetTCPConnection -LocalPort 18082 -State Listen -ErrorAction SilentlyContinue){throw 'Port 18082 is already in use; inspect the existing process first'}
$taskTestKey=(Get-Content -LiteralPath $taskToken -Raw).Trim()
if($taskTestKey.Length -lt 32){throw 'The synthetic Worker Token is invalid'}
$taskRealToken='F:/upload/.config/ocr-worker.token'
if((Test-Path -LiteralPath $taskRealToken) -and $taskTestKey -eq (Get-Content -LiteralPath $taskRealToken -Raw).Trim()){throw 'Test and production Worker Tokens must differ'}
# No upload or claim is performed by this script.
& $taskStarter -Port 18082 -Mode synthetic -SocketPort 19093 -Label ocr-ab-test -CandidateOnly -StorageRoot 'F:/upload-test' -WorkerTokenFile $taskToken
