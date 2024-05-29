git clone %1 %2
cp -r %3/* %2
cd %2
git add .
git commit -m "initial"
git push
