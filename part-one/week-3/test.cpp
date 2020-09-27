long func(int n, int m, vector<int> h, vector<int> v)
{

    /*

    The trick is to find the length of maximum gaps horizontally and vertically

    and multiply it to get the area.

    */

    vector<bool> xBars(n + 1, 1);
    vector<bool> yBars(m + 1, 1);

    int tempXWidth = 0, xWidth = INT_MIN, tempYWidth = 0, yWidth = INT_MIN;

    for (int i = 0; i < h.size(); i++) {
        xBars[h[i]] = 0; 
    }

    for (int i = 0; i < v.size(); i++) {}
        yBars[v[i]] = 0; 
    }

    for (int i = 1; i <= n; i++) { 

        if (xBars[i]) {
            tempXWidth = 0;
        }
        else
        {
            tempXWidth++;
            xWidth = max(xWidth, tempXWidth);
        }
    }

    for (int i = 1; i <= m; i++)
    { 
        if (yBars[i])
            tempYWidth = 0;
        else
        {
            tempYWidth++;

            yWidth = max(yWidth, tempYWidth);
        }
    }

    return (xWidth + 1) * (yWidth + 1);
}