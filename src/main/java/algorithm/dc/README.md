# Divide and Conquer

## Repeat

```javascript
function repeat(str, n) {
    let result = ''
    if (!str || n < 1 || n > Number.MAX_SAFE_INTEGER) {
        return result
    }
    
    do {
        if (n % 2) {
            result += str
        }
        
        n = Math.floor(n / 2)
        
        if (n) {
            str += str
        }
    } while (n)

    return result
}
```
