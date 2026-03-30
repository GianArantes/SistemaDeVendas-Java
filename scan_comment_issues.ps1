$root = 'd:\Sistema de Vendas\sistema-de-vendas'
Get-ChildItem -Path $root -Recurse -Filter *.java | ForEach-Object {
    $lines = Get-Content $_.FullName
    for ($i = 0; $i -lt $lines.Count; $i++) {
        if ($lines[$i] -match '^[ \t]*(public|protected|private)\s+(static\s+)?[\w<>,\[\] \t]+\s+\w+\s*\([^)]*\)\s*(throws\s+[\w., \t]+)?\s*\{') {
            $j = $i - 1
            while ($j -ge 0 -and $lines[$j].Trim() -eq '') { $j-- }
            if ($j -lt 0 -or $lines[$j] -notmatch '^[ \t]*/\*\*') {
                Write-Output "$($_.FullName):$($i + 1) $($lines[$i].Trim())"
            }
        }
    }
}
