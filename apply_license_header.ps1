# Copyright (C) 2026 Gianpaolo Elias Arantes (GianArantes)
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License.

$license = @(
    "Copyright (C) 2026 Jean Paulo Arantes (Gian)",
    "This program is free software: you can redistribute it and/or modify",
    "it under the terms of the GNU General Public License as published by",
    "the Free Software Foundation, either version 3 of the License."
)

$extensions = @{
    '.java' = @{start='/*'; end='*/'; prefix=' * '}
    '.xml' = @{start='<!--'; end='-->'; prefix=' '}
    '.html' = @{start='<!--'; end='-->'; prefix=' '}
    '.md' = @{start='<!--'; end='-->'; prefix=' '}
    '.properties' = @{start='#'; end=''; prefix='# '}
    '.yml' = @{start='#'; end=''; prefix='# '}
    '.yaml' = @{start='#'; end=''; prefix='# '}
    '.sql' = @{start='--'; end=''; prefix='-- '}
    '.sh' = @{start='#'; end=''; prefix='# '}
    '.bat' = @{start='REM'; end=''; prefix='REM '}
    '.cmd' = @{start='REM'; end=''; prefix='REM '}
    '.gradle' = @{start='/*'; end='*/'; prefix=' * '}
    '.kt' = @{start='/*'; end='*/'; prefix=' * '}
    '.groovy' = @{start='/*'; end='*/'; prefix=' * '}
    '.txt' = @{start='#'; end=''; prefix='# '}
    '.gitignore' = @{start='#'; end=''; prefix='# '}
}

$files = Get-ChildItem -Recurse -File | Where-Object { $extensions.ContainsKey($_.Extension.ToLower()) }

foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    if ($content -match 'Copyright \(C\) 2026 Jean Paulo Arantes \(Gian\)' -or $content -match 'GNU General Public License') {
        continue
    }
    $ext = $file.Extension.ToLower()
    $wrapper = $extensions[$ext]
    if ($wrapper.start -ne '' -and $wrapper.end -ne '') {
        $header = "$($wrapper.start)`n"
        foreach ($line in $license) {
            $header += "$($wrapper.prefix)$line`n"
        }
        $header += "$($wrapper.end)`n`n"
    } elseif ($wrapper.start -ne '' -and $wrapper.end -eq '') {
        $header = ""
        foreach ($line in $license) {
            $header += "$($wrapper.prefix)$line`n"
        }
        $header += "`n"
    } else {
        continue
    }
    Set-Content -Path $file.FullName -Value ($header + $content) -Encoding utf8
    Write-Output "Added header to $($file.FullName)"
}
