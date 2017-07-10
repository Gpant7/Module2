#include <stdio.h>
#include <stdlib.h>
#include <time.h>

typedef struct str1 node;

struct str1
{
	int C[2];
	int eval;
	int step;
	node *path;
	node *parent;
	node *previous;
	node *next;
	node *right;
	node *left;
};

node *Active, *Last, *Top, *Exported, *Start, ***Grid;
int R1[2], R2[2], **S, M, N, flag = 0;

int evaluator(int *x)
{
	int subev, hypev;

	subev = abs(x[0]-R2[0]) + abs(x[1]-R2[1]);
	hypev = subev * subev;

	if(flag==0)
		return subev;
	else
		return hypev;
}

int check(int *P)
{
	if(P[0]<0 || P[1]<0 || P[0]>(M-1) || P[1]>(N-1) || S[P[1]][P[0]]==0)
		return 0;
	else
		return 1;
}

void robot2move(void)
{
	int P[2], k;

	do
	{
		P[0] = R2[0];
		P[1] = R2[1];
		k = rand()%4;
		switch(k)
		{
			case 0:
				P[0]--;
				break;
			case 1:
				P[0]++;
				break;
			case 2:
				P[1]--;
				break;
			case 3:
				P[1]++;
				break;
		}
	}
	while(!check(P));

	R2[0] = P[0];
	R2[1] = P[1];
}

void movement(void)
{
	int i, k;
	node *move;

	move = Top;
	k = Top->step-3;

	for (i=0;i<k;i++)
		move = move->path;

	R1[0] = move->C[0];	
	R1[1] = move->C[1];
	Start = move;
}

void swap(node *x,node *y)
{
	node z, *sg;

	sg = Grid[x->C[1]][x->C[0]];
	Grid[x->C[1]][x->C[0]] = Grid[y->C[1]][y->C[0]];
	Grid[y->C[1]][y->C[0]] = sg;

	z.C[0] = y->C[0];
	z.C[1] = y->C[1];
	z.eval = y->eval;
	z.step = y->step;
	z.path = y->path;

	y->C[0] = x->C[0];
	y->C[1] = x->C[1];
	y->eval = x->eval;
	y->step = x->step;
	y->path = x->path;

	x->C[0] = z.C[0];
	x->C[1] = z.C[1];
	x->eval = z.eval;
	x->step = z.step;
	x->path = z.path;
}

void allocatelast(node *current)
{
	if(current->parent!=NULL)
	{
		if((current->eval + current->step) < (current->parent->eval + current->parent->step))
		{
			swap(current->parent, current);
			allocatelast(current->parent);
		}
	}
}

void insert(node *New)
{
	if(Top==NULL)
	{
		Top = New;
		Active = New;
		Last = New;
	}
	else
	{
		New->parent = Active;
		New->previous = Last;
		Last->next = New;

		if(Active->left==NULL)
			Active->left = New;
		else
		{
			Active->right = New;
			Active = Active->next;
		}

		Last = New;
	}
}

void newnodes(node **F)
{
	int P[4][2], l, i;
	node *new;
	
	l = 0;

	P[0][0] = Exported->C[0];
	P[0][1] = Exported->C[1]-1;
	P[1][0] = Exported->C[0]+1;
	P[1][1] = Exported->C[1];
	P[2][0] = Exported->C[0];
	P[2][1] = Exported->C[1]+1;
	P[3][0] = Exported->C[0]-1;
	P[3][1] = Exported->C[1];

	for(i=0;i<4;i++)
	{
		if(check(P[i]))
		{
			if(Grid[P[i][1]][P[i][0]]==NULL)
			{
				new = (node*)malloc(sizeof(node));
				new->C[0] = P[i][0];
				new->C[1] = P[i][1];
				new->eval = evaluator(P[i]);
				new->step = Exported->step+1;
				new->path = Exported;
				new->parent = NULL;
				new->previous = NULL;
				new->next = NULL;
				new->right = NULL;
				new->left = NULL;
				F[l] = new;
				Grid[P[i][1]][P[i][0]] = new;
				l++;
			}
			else if(Grid[P[i][1]][P[i][0]]->step > (Exported->step+1))
			{
				Grid[P[i][1]][P[i][0]]->step = Exported->step+1;
				Grid[P[i][1]][P[i][0]]->path = Exported;
				if(Grid[P[i][1]][P[i][0]]->parent==NULL && Grid[P[i][1]][P[i][0]]!=Top)
				{
					F[l] = Grid[P[i][1]][P[i][0]];
					l++;
				}
				else
					allocatelast(Grid[P[i][1]][P[i][0]]);
			}
		}
	}
}

int allocatetop(node *current)
{
	node *min;
	
	if(current->left!=NULL && current->right!=NULL)
	{
		if((current->left->eval + current->left->step) < (current->right->eval + current->right->step))
			min = current->left;
		else 
			min = current->right;
	}
	else if (current->left!=NULL)
		min = current->left;
	else 
		return 0;

	if((current->eval + current->step) > (min->eval + min->step))
	{
		swap(current,min);
		allocatetop(min);
	}

	return 0;
}

void export(void)
{
	if(Top==Last)
	{
		Exported = Top;
		Exported->parent = NULL;
		Top = NULL;
		Active = NULL;
		Last = NULL;
	}
	else
	{
		swap(Top, Last);
		Last->previous->next = NULL;
		Active = Last->parent;

		if(Active->right==Last)
			Active->right = NULL;
		else
			Active->left = NULL;

		Exported = Last;
		Last = Exported->previous;
		Exported->parent = NULL;
	}
}

void initialization(void)
{
	int i, P[4][2], T[2], k, min, max;
	node* F[4];
	node *new, *pit, *mit;

	pit = NULL;
	mit = NULL;

	for(k=0;k<N;k++)
		for(i=0;i<M;i++)
			Grid[k][i] = NULL;

	Start = (node*)malloc(sizeof(node));
	Start->C[0] = R1[0];
	Start->C[1] = R1[1];
	Start->eval = evaluator(R1);
	Start->step = 0;
	Start->path = NULL;
	Start->parent = NULL;
	Start->previous = NULL;
	Start->next = NULL;
	Start->right = NULL;
	Start->left = NULL;
	Active = Start;
	Last = Start;
	Top = Start;
	Grid[R1[1]][R1[0]] = Start;

	k = 0;
	min = 0;
	max = 0;
	P[0][0] = R1[0];
	P[0][1] = R1[1]-1;
	P[1][0] = R1[0]+1;
	P[1][1] = R1[1];
	P[2][0] = R1[0];
	P[2][1] = R1[1]+1;
	P[3][0] = R1[0]-1;
	P[3][1] = R1[1];

	for(i=0;i<4;i++)
	{
		F[i] = NULL;
		T[0] = P[i][0];
		T[1] = P[i][1];

		if(check(T))
		{
			k++;
			new = (node*)malloc(sizeof(node));
			new->C[0] = T[0];
			new->C[1] = T[1];
			new->eval = evaluator(T);
			new->step = 1;
			new->path = Start;
			new->parent = NULL;
			new->previous = NULL;
			new->next = NULL;
			new->right = NULL;
			new->left = NULL;
			F[i] = new;
		}
	}

	for(i=0;i<4;i++)
		if(F[i]!=NULL)
		{
			Grid[F[i]->C[1]][F[i]->C[0]] = F[i];

			if(F[i]->eval < min || (min==0 && pit==NULL))
			{
				min = F[i]->eval;
				pit = F[i];
			}
			if(F[i]->eval >= max)
			{
				max = F[i]->eval;
				mit = F[i];
			}
		}

	if(k==0)
	{
		Top = NULL;
	}
	else if(k==1)
	{
		Active = pit;
		Last = pit;
		Top = pit;
	}
	else if(k==2)
	{
		Active = pit;
		Last = mit;
		Top = pit;
		Last->parent = Top;
		Last->previous = Top;
		Top->next = Last;
		Top->left = Last;
	}
	else if(k==3)
	{
		i=0;
		Last = mit;
		Top = pit;

		while(F[i]==NULL || F[i]==Top || F[i]==Last)
			i++;

		Active = F[i];
		Active->parent = Top;
		Active->previous = Top;
		Active->next = Last;
		Last->parent = Top;
		Last->previous = Active;
		Top->next = Active;
		Top->right = Last;
		Top->left = Active;
	}
	else
	{
		Last = mit;
		Top = pit;

		for(i=0;i<4;i++)
			if(F[i]!=Top && F[i]!=Last)
			{
				if(Top->left==NULL)
					Top->left = F[i];
				else
					Top->right = F[i];
			}

		Active = Top->left;
		Active->parent = Top;
		Active->previous = Top;
		Active->next = Top->right;
		Active->left = Last;
		Last->parent = Active;
		Last->previous = Top->right;
		Top->next = Active;
		Top->right->parent = Top;
		Top->right->previous = Active;
		Top->right->next = Last;
	}
}

void freedom(void)
{
	int i, j;

	for(i=0;i<N;i++)
		for(j=0;j<M;j++)
			if(Grid[i][j]!=NULL)
				free(Grid[i][j]);
}

void robot1move(void)
{
	int i;
	node* F[3];

	initialization();

	while(Top!=NULL && Top->eval>0)
	{
		export();

		if(Top!=NULL)
			allocatetop(Top);

		for(i=0;i<3;i++)
			F[i] = NULL;

		newnodes(F);

		for(i=0;i<3;i++)
			if(F[i]!=NULL)
			{
				insert(F[i]);
				allocatelast(Last);
			}
	}

	if(Top!=NULL)
	{
		movement();
		freedom();
	}
}

int main(void)
{
	int i, j, XS[2], XH[2], Sflag = 1, Hflag = 1;
	char *c;
	node dummy;

	srand(time(NULL));
	scanf("%d", &M);
	scanf("%d", &N);
	scanf("%d", &R1[0]);
	scanf("%d", &R1[1]);
	scanf("%d", &R2[0]);
	scanf("%d", &R2[1]);

	R1[0]--;
	R1[1]--;
	R2[0]--;
	R2[1]--;

	XS[0] = XH[0] = R1[0];
	XS[1] = XH[1] = R1[1];

	Top = &dummy;

	S = malloc(N * sizeof(int*));
	Grid = malloc(N * sizeof(node**));

	for(i=0;i<N;i++)
	{
		S[i] = malloc(M * sizeof(int));
		Grid[i] = malloc((M) * sizeof(node*));
	}

	c = malloc(M * sizeof(char));

	for(i=0;i<N;i++)
	{
		scanf("%s", c);

		for(j=0;j<M;j++)
		{
			if(c[j]=='O')
				S[i][j] = 1;
			else
				S[i][j] = 0;
		}
	}
	
	if(!check(R1))
	{
		printf("Robot1 starts on an obstacle and it will fall!\n");
		return 0;
	}

	if(!check(R2))
	{
		printf("Robot2 starts on an obstacle and it will fall!\n");
		return 0;
	}

	while((Sflag || Hflag) && Top!=NULL)
	{
		robot2move();
		printf("R2: %2d,%2d", R2[0], R2[1]);

		if(evaluator(XS)==0)
			Sflag = 0;

		if(evaluator(XH)==0)
			Hflag = 0;

		if(evaluator(XS)>0 && Sflag)
		{
			flag = 0;
			R1[0] = XS[0];
			R1[1] = XS[1];
			robot1move();
			XS[0] = R1[0];
			XS[1] = R1[1];
			printf(" - R1sub: %2d,%2d", XS[0], XS[1]);

			if(evaluator(XS)==0)
				Sflag = 0;
		}
		else
			printf(" -             ");

		if(evaluator(XH)>0 && Hflag)
		{
			flag = 1;
			R1[0] = XH[0];
			R1[1] = XH[1];
			robot1move();
			XH[0] = R1[0];
			XH[1] = R1[1];
			printf(" - R1hyp: %2d,%2d", XH[0], XH[1]);

			if(evaluator(XH)==0)
				Hflag = 0;
		}
		else
			printf(" -             ");
		printf("\n");
	}

	if(Top==NULL)
		printf("Robot1 can't reach Robot2 due to obstacles!\n");

	return 0;
}
