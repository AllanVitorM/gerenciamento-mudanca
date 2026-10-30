$apiPath = $PSScriptRoot
$envPath = Join-Path (Split-Path $apiPath -Parent) ".env"


if(-not (Test-Path $envPath)){
    Write-Error "Arquivo .env não encontrado!"
    exit 1
}

Get-Content $envPath | ForEach-Object {
    $linha = $_.Trim()

    if($linha -and -not $linha.StartsWith("#")) {
        $partes = $linha -split "=", 2

        if($partes.Count -eq 2) {
            $chave = $partes[0].Trim()
            $valor = $partes[1].Trim()

            $valor = $valor.Trim('"', "'")

            [Environment]::SetEnvironmentVariable(
                $chave,
                $valor,
                "process"
            )
        }
    }

}

Write-Host "Variáveis do .env carregadas."

Set-Location $apiPath
.\mvnw.cmd spring-boot:run