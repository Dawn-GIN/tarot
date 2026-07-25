$outputDir = "d:\DAWN\tarot-ai\tarot-ui\public\cards"
$baseUrl = "https://raw.githubusercontent.com/metabismuth/tarot-json/master/cards/"

# Mapping: local filename => GitHub filename
# Major arcana: m{0-21}.jpg
# Minor arcana: {suit}{rank}.jpg where suit=c/p/s/w, rank=01-14
$failed = @(
    @{ file = "12-hanged-man.jpg"; gh = "m12.jpg" }
    @{ file = "17-star.jpg"; gh = "m17.jpg" }
    @{ file = "27-wands-6.jpg"; gh = "w06.jpg" }
    @{ file = "28-wands-7.jpg"; gh = "w07.jpg" }
    @{ file = "29-wands-8.jpg"; gh = "w08.jpg" }
    @{ file = "30-wands-9.jpg"; gh = "w09.jpg" }
    @{ file = "31-wands-10.jpg"; gh = "w10.jpg" }
    @{ file = "32-wands-page.jpg"; gh = "w11.jpg" }
    @{ file = "33-wands-knight.jpg"; gh = "w12.jpg" }
    @{ file = "35-wands-king.jpg"; gh = "w14.jpg" }
    @{ file = "36-cups-ace.jpg"; gh = "c01.jpg" }
    @{ file = "38-cups-3.jpg"; gh = "c03.jpg" }
    @{ file = "39-cups-4.jpg"; gh = "c04.jpg" }
    @{ file = "44-cups-9.jpg"; gh = "c09.jpg" }
    @{ file = "46-cups-page.jpg"; gh = "c11.jpg" }
    @{ file = "56-swords-7.jpg"; gh = "s07.jpg" }
    @{ file = "57-swords-8.jpg"; gh = "s08.jpg" }
    @{ file = "59-swords-10.jpg"; gh = "s10.jpg" }
    @{ file = "61-swords-knight.jpg"; gh = "s12.jpg" }
    @{ file = "67-pentacles-4.jpg"; gh = "p04.jpg" }
    @{ file = "71-pentacles-8.jpg"; gh = "p08.jpg" }
)

$success = 0
Write-Host "Downloading remaining 21 cards from GitHub..."
Write-Host ""

foreach ($card in $failed) {
    $outFile = Join-Path $outputDir $card.file
    $url = $baseUrl + $card.gh
    try {
        Invoke-WebRequest -Uri $url -OutFile $outFile -UseBasicParsing -ErrorAction Stop
        $size = [math]::Round((Get-Item $outFile).Length / 1024)
        Write-Host "  OK: $($card.file) (${size}KB)"
        $success++
    } catch {
        Write-Host "  FAIL: $($card.file) - $($_.Exception.Message)"
    }
    Start-Sleep -Milliseconds 500
}

Write-Host ""
Write-Host "Done! $success / $($failed.Count) downloaded."

# Final check
$total = (Get-ChildItem (Join-Path $outputDir "*.jpg")).Count
Write-Host "Total cards in folder: $total / 78"
