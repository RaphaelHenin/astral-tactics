param([Parameter(Mandatory=$true)][string]$Destination)
$ErrorActionPreference = 'Stop'
$source = Split-Path $PSScriptRoot -Parent
$target = [System.IO.Path]::GetFullPath($Destination)
if ($target.StartsWith($source + [System.IO.Path]::DirectorySeparatorChar, [System.StringComparison]::OrdinalIgnoreCase) -or $target -eq $source) {
    throw 'Choisir un dossier de destination distinct du dossier extrait.'
}
$files = @(Get-ChildItem -LiteralPath $source -Recurse -File -Force)
$entries = @($files | ForEach-Object {
    $relative = $_.FullName.Substring($source.Length).TrimStart([char[]]'\/')
    [pscustomobject]@{ Source = $_.FullName; Target = Join-Path $target $relative }
})
$conflicts = @($entries | Where-Object { Test-Path -LiteralPath $_.Target })
if ($conflicts.Count -gt 0) {
    $conflicts | ForEach-Object { Write-Host $_.Target }
    throw 'Import interrompu avant copie : ces chemins existent deja. Aucun fichier remplace.'
}
foreach ($entry in $entries) {
    [System.IO.Directory]::CreateDirectory((Split-Path $entry.Target -Parent)) | Out-Null
    # overwrite=false: also protects against a file created after the preflight.
    [System.IO.File]::Copy($entry.Source, $entry.Target, $false)
}
Write-Host "Sources importees dans $target. Le depot Git existant est conserve."
