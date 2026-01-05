<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;

class AdminBasicAuth
{
    public function handle(Request $request, Closure $next)
    {
        $user = env('ADMIN_USER', 'admin');
        $pass = env('ADMIN_PASS', 'admin');

        $providedUser = $request->getUser();
        $providedPass = $request->getPassword();

        if ($providedUser !== $user || $providedPass !== $pass) {
            return response('Unauthorized', 401, [
                'WWW-Authenticate' => 'Basic realm="Admin Area"',
            ]);
        }

        return $next($request);
    }
}


