import { describe, test, expect, beforeEach, vi } from 'vitest';
import { WebAPITodosService } from '../../src/application/todo-service.js';

describe('WebAPITodosService', () => {
  let service;
  const apiUrl = '/api/todos';

  beforeEach(() => {
    service = WebAPITodosService(apiUrl);
    global.fetch = vi.fn();
  });

  describe('all', () => {
    test('모든 todos를 성공적으로 가져옴', async () => {
      const mockTodos = [
        { id: '1', text: 'Task 1', completed: false },
        { id: '2', text: 'Task 2', completed: true }
      ];

      global.fetch.mockResolvedValueOnce({
        status: 200,
        json: async () => mockTodos
      });

      const result = await service.all();

      expect(global.fetch).toHaveBeenCalledWith(apiUrl);
      expect(result).toEqual(mockTodos);
    });

    test('서버 에러 시 예외 발생', async () => {
      global.fetch.mockResolvedValueOnce({
        status: 500,
        json: async () => ({
          error: 'InternalServerError',
          message: 'Server error occurred'
        })
      });

      await expect(service.all()).rejects.toThrow('Server error occurred');
    });
  });

  describe('add', () => {
    test('새로운 todo를 성공적으로 추가', async () => {
      const newTodo = { id: '3', text: 'Task 3', completed: false, createdAt: Date.now(), updatedAt: null };
      const responseTodo = { ...newTodo };

      global.fetch.mockResolvedValueOnce({
        status: 201,
        json: async () => responseTodo
      });

      const result = await service.add(newTodo);

      expect(global.fetch).toHaveBeenCalledWith(apiUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newTodo)
      });
      expect(result).toEqual(responseTodo);
    });

    test('유효성 검증 실패 시 에러 정보 포함', async () => {
      const invalidTodo = { id: '3', text: '', completed: false };

      global.fetch.mockResolvedValueOnce({
        status: 400,
        json: async () => ({
          error: 'ValidationError',
          message: 'Validation failed',
          errors: ['Text must not be empty']
        })
      });

      try {
        await service.add(invalidTodo);
      } catch (error) {
        expect(error.message).toBe('Validation failed');
        expect(error.name).toBe('ValidationError');
        expect(error.details).toEqual(['Text must not be empty']);
      }
    });
  });

  describe('edit', () => {
    test('기존 todo를 성공적으로 수정', async () => {
      const updatedTodo = { id: '1', text: 'Updated Task', completed: true, updatedAt: Date.now() };

      global.fetch.mockResolvedValueOnce({
        status: 200,
        json: async () => updatedTodo
      });

      const result = await service.edit(updatedTodo);

      expect(global.fetch).toHaveBeenCalledWith(`${apiUrl}/1`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updatedTodo)
      });
      expect(result).toEqual(updatedTodo);
    });

    test('존재하지 않는 todo 수정 시 에러 발생', async () => {
      const updatedTodo = { id: '999', text: 'Non-existent', completed: false };

      global.fetch.mockResolvedValueOnce({
        status: 404,
        json: async () => ({
          error: 'NotFound',
          message: 'Todo not found'
        })
      });

      await expect(service.edit(updatedTodo)).rejects.toThrow('Todo not found');
    });
  });

  describe('remove', () => {
    test('todo를 성공적으로 삭제', async () => {
      global.fetch.mockResolvedValueOnce({
        status: 204,
        json: async () => null
      });

      await service.remove('1');

      expect(global.fetch).toHaveBeenCalledWith(`${apiUrl}/1`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' }
      });
    });

    test('존재하지 않는 todo 삭제 시 에러 발생', async () => {
      global.fetch.mockResolvedValueOnce({
        status: 404,
        json: async () => ({
          error: 'NotFound',
          message: 'Todo not found'
        })
      });

      await expect(service.remove('999')).rejects.toThrow('Todo not found');
    });
  });

  describe('clearCompleted', () => {
    test('완료된 todo들을 모두 삭제', async () => {
      const mockTodos = [
        { id: '1', text: 'Task 1', completed: false },
        { id: '2', text: 'Task 2', completed: true },
        { id: '3', text: 'Task 3', completed: true }
      ];

      // all() 호출
      global.fetch.mockResolvedValueOnce({
        status: 200,
        json: async () => mockTodos
      });

      // remove('2') 호출
      global.fetch.mockResolvedValueOnce({
        status: 204,
        json: async () => null
      });

      // remove('3') 호출
      global.fetch.mockResolvedValueOnce({
        status: 204,
        json: async () => null
      });

      await service.clearCompleted();

      expect(global.fetch).toHaveBeenCalledTimes(3);
      expect(global.fetch).toHaveBeenNthCalledWith(1, apiUrl);
      expect(global.fetch).toHaveBeenNthCalledWith(2, `${apiUrl}/2`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' }
      });
      expect(global.fetch).toHaveBeenNthCalledWith(3, `${apiUrl}/3`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' }
      });
    });

    test('완료된 todo가 없으면 삭제 요청하지 않음', async () => {
      const mockTodos = [
        { id: '1', text: 'Task 1', completed: false },
        { id: '2', text: 'Task 2', completed: false }
      ];

      global.fetch.mockResolvedValueOnce({
        status: 200,
        json: async () => mockTodos
      });

      await service.clearCompleted();

      expect(global.fetch).toHaveBeenCalledTimes(1);
      expect(global.fetch).toHaveBeenCalledWith(apiUrl);
    });

    test('all() 호출 실패 시 에러 발생', async () => {
      global.fetch.mockResolvedValueOnce({
        status: 500,
        json: async () => ({
          error: 'InternalServerError',
          message: 'Server error'
        })
      });

      await expect(service.clearCompleted()).rejects.toThrow('Server error');
    });

    test('일부 remove 실패 시 에러 발생', async () => {
      const mockTodos = [
        { id: '1', text: 'Task 1', completed: true },
        { id: '2', text: 'Task 2', completed: true }
      ];

      global.fetch.mockResolvedValueOnce({
        status: 200,
        json: async () => mockTodos
      });

      global.fetch.mockResolvedValueOnce({
        status: 204,
        json: async () => null
      });

      global.fetch.mockResolvedValueOnce({
        status: 404,
        json: async () => ({
          error: 'NotFound',
          message: 'Todo not found'
        })
      });

      await expect(service.clearCompleted()).rejects.toThrow('Todo not found');
    });
  });

  describe('에러 핸들링', () => {
    test('JSON 파싱 실패 시 기본 에러 메시지 사용', async () => {
      global.fetch.mockResolvedValueOnce({
        status: 500,
        json: async () => {
          throw new Error('JSON parse error');
        }
      });

      try {
        await service.all();
      } catch (error) {
        expect(error.message).toBe('Failed to process in server');
        expect(error.name).toBe('Unknown Error');
      }
    });

    test('errors 배열에 객체가 있을 때 defaultMessage 추출', async () => {
      global.fetch.mockResolvedValueOnce({
        status: 400,
        json: async () => ({
          error: 'ValidationError',
          message: 'Validation failed',
          errors: [
            { field: 'text', defaultMessage: 'Text is required' },
            { field: 'completed', defaultMessage: 'Completed must be boolean' }
          ]
        })
      });

      try {
        await service.add({ id: '1', text: '', completed: 'invalid' });
      } catch (error) {
        expect(error.details).toEqual(['Text is required', 'Completed must be boolean']);
      }
    });

    test('성공 응답의 JSON 파싱 실패 시 null 반환', async () => {
      global.fetch.mockResolvedValueOnce({
        status: 204,
        json: async () => {
          throw new Error('No content');
        }
      });

      const result = await service.remove('1');
      expect(result).toBeNull();
    });
  });
});
