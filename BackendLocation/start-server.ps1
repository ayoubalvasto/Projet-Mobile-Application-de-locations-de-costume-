# Script pour démarrer le serveur Laravel accessible depuis le réseau local
# Usage: .\start-server.ps1

Write-Host "Démarrage du serveur Laravel sur 0.0.0.0:8000..." -ForegroundColor Green
Write-Host "Le serveur sera accessible depuis votre téléphone via: http://192.168.27.48:8000" -ForegroundColor Yellow
Write-Host ""
Write-Host "Pour arrêter le serveur, appuyez sur Ctrl+C" -ForegroundColor Cyan
Write-Host ""

cd $PSScriptRoot
php artisan serve --host 0.0.0.0 --port 8000


