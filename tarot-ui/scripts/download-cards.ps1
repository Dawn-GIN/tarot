$outputDir = "d:\DAWN\tarot-ai\tarot-ui\public\cards"
$baseUrl = "https://commons.wikimedia.org/wiki/Special:FilePath/"

# All 78 cards: {local filename} => {Wikimedia filename}
$cards = @(
    # Major Arcana
    @{ file = "00-fool.jpg"; wiki = "RWS_Tarot_00_Fool.jpg" }
    @{ file = "01-magician.jpg"; wiki = "RWS_Tarot_01_Magician.jpg" }
    @{ file = "02-high-priestess.jpg"; wiki = "RWS_Tarot_02_High_Priestess.jpg" }
    @{ file = "03-empress.jpg"; wiki = "RWS_Tarot_03_Empress.jpg" }
    @{ file = "04-emperor.jpg"; wiki = "RWS_Tarot_04_Emperor.jpg" }
    @{ file = "05-hierophant.jpg"; wiki = "RWS_Tarot_05_Hierophant.jpg" }
    @{ file = "06-lovers.jpg"; wiki = "TheLovers.jpg" }
    @{ file = "07-chariot.jpg"; wiki = "RWS_Tarot_07_Chariot.jpg" }
    @{ file = "08-strength.jpg"; wiki = "RWS_Tarot_08_Strength.jpg" }
    @{ file = "09-hermit.jpg"; wiki = "RWS_Tarot_09_Hermit.jpg" }
    @{ file = "10-wheel-of-fortune.jpg"; wiki = "RWS_Tarot_10_Wheel_of_Fortune.jpg" }
    @{ file = "11-justice.jpg"; wiki = "RWS_Tarot_11_Justice.jpg" }
    @{ file = "12-hanged-man.jpg"; wiki = "RWS_Tarot_12_Hanged_Man.jpg" }
    @{ file = "13-death.jpg"; wiki = "RWS_Tarot_13_Death.jpg" }
    @{ file = "14-temperance.jpg"; wiki = "RWS_Tarot_14_Temperance.jpg" }
    @{ file = "15-devil.jpg"; wiki = "RWS_Tarot_15_Devil.jpg" }
    @{ file = "16-tower.jpg"; wiki = "RWS_Tarot_16_Tower.jpg" }
    @{ file = "17-star.jpg"; wiki = "RWS_Tarot_17_Star.jpg" }
    @{ file = "18-moon.jpg"; wiki = "RWS_Tarot_18_Moon.jpg" }
    @{ file = "19-sun.jpg"; wiki = "RWS_Tarot_19_Sun.jpg" }
    @{ file = "20-judgement.jpg"; wiki = "RWS_Tarot_20_Judgement.jpg" }
    @{ file = "21-world.jpg"; wiki = "RWS_Tarot_21_World.jpg" }
    # Wands
    @{ file = "22-wands-ace.jpg"; wiki = "Wands01.jpg" }
    @{ file = "23-wands-2.jpg"; wiki = "Wands02.jpg" }
    @{ file = "24-wands-3.jpg"; wiki = "Wands03.jpg" }
    @{ file = "25-wands-4.jpg"; wiki = "Wands04.jpg" }
    @{ file = "26-wands-5.jpg"; wiki = "Wands05.jpg" }
    @{ file = "27-wands-6.jpg"; wiki = "Wands06.jpg" }
    @{ file = "28-wands-7.jpg"; wiki = "Wands07.jpg" }
    @{ file = "29-wands-8.jpg"; wiki = "Wands08.jpg" }
    @{ file = "30-wands-9.jpg"; wiki = "Wands09.jpg" }
    @{ file = "31-wands-10.jpg"; wiki = "Wands10.jpg" }
    @{ file = "32-wands-page.jpg"; wiki = "Wands11.jpg" }
    @{ file = "33-wands-knight.jpg"; wiki = "Wands12.jpg" }
    @{ file = "34-wands-queen.jpg"; wiki = "Wands13.jpg" }
    @{ file = "35-wands-king.jpg"; wiki = "Wands14.jpg" }
    # Cups
    @{ file = "36-cups-ace.jpg"; wiki = "Cups01.jpg" }
    @{ file = "37-cups-2.jpg"; wiki = "Cups02.jpg" }
    @{ file = "38-cups-3.jpg"; wiki = "Cups03.jpg" }
    @{ file = "39-cups-4.jpg"; wiki = "Cups04.jpg" }
    @{ file = "40-cups-5.jpg"; wiki = "Cups05.jpg" }
    @{ file = "41-cups-6.jpg"; wiki = "Cups06.jpg" }
    @{ file = "42-cups-7.jpg"; wiki = "Cups07.jpg" }
    @{ file = "43-cups-8.jpg"; wiki = "Cups08.jpg" }
    @{ file = "44-cups-9.jpg"; wiki = "Cups09.jpg" }
    @{ file = "45-cups-10.jpg"; wiki = "Cups10.jpg" }
    @{ file = "46-cups-page.jpg"; wiki = "Cups11.jpg" }
    @{ file = "47-cups-knight.jpg"; wiki = "Cups12.jpg" }
    @{ file = "48-cups-queen.jpg"; wiki = "Cups13.jpg" }
    @{ file = "49-cups-king.jpg"; wiki = "Cups14.jpg" }
    # Swords
    @{ file = "50-swords-ace.jpg"; wiki = "Swords01.jpg" }
    @{ file = "51-swords-2.jpg"; wiki = "Swords02.jpg" }
    @{ file = "52-swords-3.jpg"; wiki = "Swords03.jpg" }
    @{ file = "53-swords-4.jpg"; wiki = "Swords04.jpg" }
    @{ file = "54-swords-5.jpg"; wiki = "Swords05.jpg" }
    @{ file = "55-swords-6.jpg"; wiki = "Swords06.jpg" }
    @{ file = "56-swords-7.jpg"; wiki = "Swords07.jpg" }
    @{ file = "57-swords-8.jpg"; wiki = "Swords08.jpg" }
    @{ file = "58-swords-9.jpg"; wiki = "Swords09.jpg" }
    @{ file = "59-swords-10.jpg"; wiki = "Swords10.jpg" }
    @{ file = "60-swords-page.jpg"; wiki = "Swords11.jpg" }
    @{ file = "61-swords-knight.jpg"; wiki = "Swords12.jpg" }
    @{ file = "62-swords-queen.jpg"; wiki = "Swords13.jpg" }
    @{ file = "63-swords-king.jpg"; wiki = "Swords14.jpg" }
    # Pentacles
    @{ file = "64-pentacles-ace.jpg"; wiki = "Pents01.jpg" }
    @{ file = "65-pentacles-2.jpg"; wiki = "Pents02.jpg" }
    @{ file = "66-pentacles-3.jpg"; wiki = "Pents03.jpg" }
    @{ file = "67-pentacles-4.jpg"; wiki = "Pents04.jpg" }
    @{ file = "68-pentacles-5.jpg"; wiki = "Pents05.jpg" }
    @{ file = "69-pentacles-6.jpg"; wiki = "Pents06.jpg" }
    @{ file = "70-pentacles-7.jpg"; wiki = "Pents07.jpg" }
    @{ file = "71-pentacles-8.jpg"; wiki = "Pents08.jpg" }
    @{ file = "72-pentacles-9.jpg"; wiki = "Pents09.jpg" }
    @{ file = "73-pentacles-10.jpg"; wiki = "Pents10.jpg" }
    @{ file = "74-pentacles-page.jpg"; wiki = "Pents11.jpg" }
    @{ file = "75-pentacles-knight.jpg"; wiki = "Pents12.jpg" }
    @{ file = "76-pentacles-queen.jpg"; wiki = "Pents13.jpg" }
    @{ file = "77-pentacles-king.jpg"; wiki = "Pents14.jpg" }
)

$total = $cards.Count
$success = 0
$failed = @()

Write-Host "Downloading $total Rider-Waite tarot cards via Special:FilePath..."
Write-Host ""

foreach ($card in $cards) {
    $outFile = Join-Path $outputDir $card.file
    if ((Test-Path $outFile) -and (Get-Item $outFile).Length -gt 10000) {
        Write-Host "  SKIP: $($card.file)"
        $success++
        continue
    }
    $url = $baseUrl + $card.wiki
    try {
        Invoke-WebRequest -Uri $url -OutFile $outFile -UseBasicParsing -ErrorAction Stop
        $size = [math]::Round((Get-Item $outFile).Length / 1024)
        Write-Host "  OK: $($card.file) (${size}KB)"
        $success++
    } catch {
        Write-Host "  FAIL: $($card.file) - $($_.Exception.Message)"
        $failed += $card.file
    }
    Start-Sleep -Milliseconds 2000
}

Write-Host ""
Write-Host "Done! $success / $total downloaded."
if ($failed.Count -gt 0) {
    Write-Host "Failed ($($failed.Count)): $($failed -join ', ')"
}
