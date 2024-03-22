filename='data.xls';
sheet=1;
priceRange='AX4:AX63';
minWeightRange='AY4:AY63';
maxWeightRange='AZ4:AZ63';
dataRange='E4:AW63';
materialNameRange='B4:B63';
elementsNameRange='E2:AW2';
matSelectionRange='BA4:BA63';

prices=xlsread(filename,sheet,priceRange);
minWeights=xlsread(filename,sheet,minWeightRange);
maxWeights=xlsread(filename,sheet,maxWeightRange);
data=xlsread(filename,sheet,dataRange);
matSelection=xlsread(filename,sheet,matSelectionRange);
[~,materialNames,~]=xlsread(filename,sheet,materialNameRange);
[~,elementsNames,~]=xlsread(filename,sheet,elementsNameRange);