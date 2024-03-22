extract_excel;

maxWeight=1000;
weightAccuracy=3;


elementAccuracy=0.02; %2 percent
elementsLength=length(elementsNames);
elementsToMatch=elementsLength;
materialsLength=length(prices);
elements=zeros(elementsLength,1);
elements(1)=2550;
elements(2)=18;
elements(3)=0.5;
elements(4)=0.4;
elements(5)=1550;

lb=minWeights;
ub=maxWeights;
for j=1:materialsLength
   if matSelection(j)==0
      lb(j)=0;
      ub(j)=0;
   end
end
f=prices;
Aeq=zeros(1,materialsLength);
beq=zeros(1,1);
A=zeros(elementsToMatch*2+2,materialsLength);
b=zeros(elementsToMatch*2+2,1);
for i=1:elementsLength
   if elements(i)>0
        b(i)=maxWeight*(elements(i)+elements(i)*elementAccuracy);
        b(i+elementsLength)=-maxWeight*(elements(i)-elements(i)*elementAccuracy);
        
        A(i,1:60)=transpose(data(1:60,i));
        A(i+elementsLength,1:60)=-transpose(data(1:60,i));
   end
end
 
b(elementsToMatch*2+1)=maxWeight+weightAccuracy;
b(elementsToMatch*2+2)=-maxWeight+weightAccuracy;

A(elementsToMatch*2+1,1:60)=ones(1,materialsLength);
A(elementsToMatch*2+2,1:60)=-ones(1,materialsLength);

[x, fval] = linprog(f,A,b,Aeq,beq,lb,ub);
weight=sum(x);
for d = 1:materialsLength
  fprintf('%6.3f \t%s\n',x(d))
end

fprintf('\n%6.3f \t%s\n',fval/weight)