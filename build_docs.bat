echo "Generating project documentation"

.\texdoclet\javadoc_to_latex.bat

if %ERRORLEVEL% >= 1 (
    echo "Generation of LaTeX JavaDoc failed. [FAILED]"
    exit %ERRORLEVEL%
)

if %ERRORLEVEL% <= -1 (
    echo "Generation of LaTeX JavaDoc failed. [FAILED]"
    exit %ERRORLEVEL%
)

cd .\docs

pdflatex -halt-on-error szoftlab4.tex
pdflatex -halt-on-error szoftlab4.tex
pdflatex -halt-on-error szoftlab4.tex

if %ERRORLEVEL% >= 1 (
    echo "Generating of the documentation ended. [FAILED]"
    exit %ERRORLEVEL%
)

if %ERRORLEVEL% <= -1 (
    echo "Generating of the documentation ended. [FAILED]"
    exit %ERRORLEVEL%
)

echo "Generating of the documentation ended. [SUCCESS]"

del /S *.aux
del /S *.lof
del /S *.log
del /S *.out
del /S *.toc

exit 0

