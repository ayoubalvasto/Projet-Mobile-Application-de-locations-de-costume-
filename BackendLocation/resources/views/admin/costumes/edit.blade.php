@extends('admin.layout')

@section('title', 'Admin - Modifier costume')

@section('content')
    <h1 class="h3 mb-3">Modifier le costume #{{ $costume->id }}</h1>

    <div class="card p-3">
        <form method="POST" action="{{ route('admin.costumes.update', $costume) }}">
            @method('PUT')
            @include('admin.costumes.form', ['costume' => $costume])
            <button class="btn btn-primary">Enregistrer</button>
            <a class="btn btn-link" href="{{ route('admin.costumes.index') }}">Retour</a>
        </form>
    </div>
@endsection


